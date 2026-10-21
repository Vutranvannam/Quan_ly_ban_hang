import psycopg2
import bcrypt
import datetime
import os

# =========================
# CONFIG (ENV - Docker friendly)
# =========================
DB_HOST = os.environ.get("DB_HOST", "localhost")
DB_PORT = os.environ.get("DB_PORT", "5432")
DB_NAME = os.environ.get("DB_NAME", "qly_kho")
DB_USER = os.environ.get("DB_USER", "postgres")
DB_PASS = os.environ.get("DB_PASSWORD", "231005")

# =========================
# INPUT
# =========================
USERNAME = "admin"
PASSWORD = "123456"

# =========================
# DB CONNECT
# =========================
def get_connection():
    return psycopg2.connect(
        host=DB_HOST,
        port=DB_PORT,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASS
    )

# =========================
# PASSWORD HASH (bcrypt + Spring compatibility)
# =========================
def hash_password(password: str) -> str:
    hashed = bcrypt.hashpw(password.encode("utf-8"), bcrypt.gensalt()).decode("utf-8")

    # Spring Security compatibility fix
    if hashed.startswith("$2b$"):
        hashed = "$2a$" + hashed[4:]

    return hashed

# =========================
# MAIN LOGIC
# =========================
def reset_password():
    conn = None

    try:
        print(f"[INFO] Connecting DB {DB_HOST}:{DB_PORT}...")
        conn = get_connection()
        cur = conn.cursor()

        # 1. Check user exists
        cur.execute("SELECT id FROM users WHERE username = %s", (USERNAME,))
        user = cur.fetchone()

        if not user:
            print(f"[ERROR] User '{USERNAME}' not found")
            return

        user_id = user[0]

        # 2. Hash new password
        print("[INFO] Hashing new password...")
        new_hash = hash_password(PASSWORD)

        # 3. Update password
        cur.execute("""
            UPDATE users
            SET password_hash = %s,
                updated_at = %s
            WHERE username = %s
        """, (new_hash, datetime.datetime.now(), USERNAME))

        conn.commit()

        print(f"[SUCCESS] Password reset for {USERNAME}")

    except Exception as e:
        if conn:
            conn.rollback()
        print(f"[ERROR] {e}")

    finally:
        if conn:
            conn.close()
            print("[INFO] DB connection closed")

# =========================
# RUN
# =========================
if __name__ == "__main__":
    reset_password()