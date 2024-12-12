from flask import Flask, jsonify, request
import os
from dotenv import load_dotenv
import imaplib
import email
from email.header import decode_header
import requests

# Initialize Flask app
app = Flask(__name__)

# Load environment variables
load_dotenv()

# Define constants
EMAIL_USER = os.getenv("EMAIL_USER")
EMAIL_PASSWORD = os.getenv("EMAIL_PASSWORD")
IMAP_SERVER = "imap.gmail.com"
IMAP_PORT = 993
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
GITHUB_REPO = os.getenv("GITHUB_REPO")
WORKFLOW_FILE = os.getenv("WORKFLOW_FILE")
PROCESSED_EMAILS_FILE = "processed_emails.txt"

@app.route('/trigger', methods=['POST'])
def trigger_email_check():
    # Load processed UIDs
    try:
        with open(PROCESSED_EMAILS_FILE, "r") as file:
            processed_uids = set(file.read().splitlines())
    except FileNotFoundError:
        processed_uids = set()

    # Connect to the email server
    try:
        mail = imaplib.IMAP4_SSL(IMAP_SERVER)
        mail.login(EMAIL_USER, EMAIL_PASSWORD)
        mail.select("inbox")
    except Exception as e:
        return jsonify({"error": f"Failed to connect to email server: {e}"}), 500

    # Search for unread emails with a specific subject
    status, messages = mail.search(None, '(UNSEEN SUBJECT "Run Automation Test")')

    if status == "OK":
        for num in messages[0].split():
            # Fetch the unique ID of the email
            status, uid_data = mail.fetch(num, "(UID)")
            if status != "OK":
                continue
            uid = uid_data[0].split()[2].decode()

            # Skip the email if it's already processed
            if uid in processed_uids:
                continue

            # Fetch the email content
            status, msg_data = mail.fetch(num, "(RFC822)")
            if status != "OK":
                continue

            for response_part in msg_data:
                if isinstance(response_part, tuple):
                    msg = email.message_from_bytes(response_part[1])
                    subject, encoding = decode_header(msg["Subject"])[0]
                    if isinstance(subject, bytes):
                        subject = subject.decode(encoding or "utf-8")

                    # Get the email body
                    email_body = ""
                    if msg.is_multipart():
                        for part in msg.walk():
                            content_type = part.get_content_type()
                            content_disposition = str(part.get("Content-Disposition"))
                            if content_type == "text/plain" and "attachment" not in content_disposition:
                                email_body = part.get_payload(decode=True).decode()
                                break
                            elif content_type == "text/html" and "attachment" not in content_disposition and not email_body:
                                email_body = part.get_payload(decode=True).decode()
                                break
                    else:
                        email_body = msg.get_payload(decode=True).decode()

                    # Send API request to trigger GitHub Action
                    url = f"https://api.github.com/repos/{GITHUB_REPO}/actions/workflows/{WORKFLOW_FILE}/dispatches"
                    headers = {
                        "Authorization": f"token {GITHUB_TOKEN}",
                        "Accept": "application/vnd.github.v3+json"
                    }
                    data = {
                        "ref": "main",
                        "inputs": {
                            "email_subject": subject,
                            "email_body": email_body
                        }
                    }

                    response = requests.post(url, json=data, headers=headers)
                    if response.status_code not in [201, 204]:
                        return jsonify({"error": f"Failed to trigger GitHub Action: {response.status_code}"}), 500

                    # Mark this email UID as processed
                    processed_uids.add(uid)
                    mail.store(num, '+FLAGS', '\\Seen')

        # Save the processed UIDs to file
        with open(PROCESSED_EMAILS_FILE, "w") as file:
            file.write("\n".join(processed_uids))

    mail.logout()
    return jsonify({"message": "Email check completed and GitHub Action triggered if applicable."})

# Run Flask app
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
