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
EMAIL = "phucnasa2310@gmail.com"
FULL_NAME = "NMPhuc"

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
    hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

    # Fix Spring Security compatibility ($2b -> $2a)
    if hashed.startswith("$2b$"):
        hashed = "$2a$" + hashed[4:]

    return hashed

# =========================
# MAIN LOGIC
# =========================
def insert_user():
    conn = None
    
    try:
        print(f"[INFO] Connecting to {DB_HOST}:{DB_PORT} ...")
        conn = get_connection()
        cur = conn.cursor()

        # hash password
        print("[INFO] Hashing password...")
        password_hash = hash_password(PASSWORD)

        # check user exists
        cur.execute(
            "SELECT id FROM users WHERE username = %s",
            (USERNAME,)
        )
        row = cur.fetchone()

        now = datetime.datetime.now()

        if row:
            user_id = row[0]
            print(f"[INFO] User exists (id={user_id}) → updating password...")

            cur.execute("""
                UPDATE users
                SET password_hash = %s,
                    email = %s,
                    full_name = %s,
                    updated_at = %s
                WHERE username = %s
            """, (password_hash, EMAIL, FULL_NAME, now, USERNAME))

            conn.commit()
            print(f"[SUCCESS] Updated user '{USERNAME}'")
            return
        
        print(f"[INFO] User not found → inserting new user '{USERNAME}'")

        cur.execute("""
            INSERT INTO users (
                username,
                email,
                password_hash,
                full_name,
                enabled,
                account_non_expired,
                account_non_locked,
                credentials_non_expired,
                auth_version,
                permission_version,
                created_at,
                updated_at
            )
            VALUES (
                %s, %s, %s, %s,
                TRUE, TRUE, TRUE, TRUE,
                %s, %s,
                %s, %s
            )
        """, (
            USERNAME,
            EMAIL,
            password_hash,
            FULL_NAME,
            1,
            1,
            now,
            now
        ))

        conn.commit()
        print(f"[SUCCESS] Inserted user '{USERNAME}'")

        cur.close()

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
    insert_user()