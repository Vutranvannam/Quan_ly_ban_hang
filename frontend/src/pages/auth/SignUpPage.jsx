import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

import FloatingInput from "../../components/auth/AuthFloatingInput";
import AuthCheckBox from "../../components/auth/AuthCheckBox";
import AuthButton from "../../components/auth/AuthButton";
import AuthLayout from "../../layouts/AuthLayout";
import AuthFooter from "../../components/auth/AuthFooter";
import AuthMessage from "../../components/auth/AuthMessage";

function PagesSignUp() {
  const navigate = useNavigate();
  const { register } = useAuth();

  const [username, setUsername] = useState("");
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [agree, setAgree] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    // validate
    if (!username || !email || !fullName || !password || !confirmPassword) {
      setError("Vui lòng điền đầy đủ thông tin");
      return;
    }

    if (!email.includes("@")) {
      setError("Email không hợp lệ");
      return;
    }

    if (!agree) {
      setError("Bạn phải đồng ý với điều khoản sử dụng");
      return;
    }

    if (password !== confirmPassword) {
      setError("Mật khẩu không khớp");
      return;
    }

    try {
      setError("");

      await register(
        {
          username,
          password,
          fullName,
          email,
        },
      );

      navigate("/auth/sign-in"); // chuyển về đăng nhập
    } catch (err) {
      setError(err.message || "Đăng ký thất bại");
    }
  };

  return (
    <AuthLayout>
      <AuthMessage
        title="Tạo tài khoản mới"
        note="Đăng ký để tiếp tục sử dụng hệ thống."
        titleClass="mb-2"
      />

      <form onSubmit={handleSubmit}>
        <div className="row">
          <FloatingInput
            type="text"
            label="Họ và tên"
            value={fullName}
            onChange={(e) => setFullName(e.target.value)}
          />

          <FloatingInput
            type="email"
            label="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <FloatingInput
            type="text"
            label="Tài khoản"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <FloatingInput
            type="password"
            label="Mật khẩu"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <FloatingInput
            type="password"
            label="Xác nhận mật khẩu"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          <AuthCheckBox
            id="agree"
            label="Tôi đồng ý với điều khoản sử dụng"
            col="col-lg-12"
            checked={agree}
            onChange={(e) => setAgree(e.target.checked)}
          />
        </div>

        {error && (
          <p style={{ color: "red", marginTop: "10px" }}>{error}</p>
        )}

        <AuthButton text="Đăng ký" />

        <AuthFooter
          text="Đã có tài khoản?"
          linkText="Đăng nhập"
          href="/auth/sign-in"
        />
      </form>
    </AuthLayout>
  );
}

export default PagesSignUp;