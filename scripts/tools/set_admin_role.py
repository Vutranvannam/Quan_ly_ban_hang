import psycopg2
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
ROLENAME = "ADMIN"

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
# MAIN LOGIC
# =========================
def set_admin_role():
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

        # 2. Get role id
        cur.execute("SELECT id FROM roles WHERE name = %s", (ROLENAME,))
        role = cur.fetchone()

        if not role:
            print(f"[INFO] Role '{ROLENAME}' not found → creating...")

            cur.execute("INSERT INTO roles (name) VALUES (%s) RETURNING id", (ROLENAME,))
            role_id = cur.fetchone()[0]
        else:
            role_id = role[0]

        # 3. Check if user already has role
        cur.execute("""
            SELECT 1 FROM user_roles
            WHERE user_id = %s AND role_id = %s
        """, (user_id, role_id))

        if cur.fetchone():
            print(f"[INFO] User already has ADMIN role")

            conn.commit()
            return

        # 4. Insert role mapping
        cur.execute("""
            INSERT INTO user_roles (user_id, role_id)
            VALUES (%s, %s)
        """, (user_id, role_id))

        conn.commit()

        print(f"[SUCCESS] ADMIN role assigned to {USERNAME}")
    
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
    set_admin_role()

