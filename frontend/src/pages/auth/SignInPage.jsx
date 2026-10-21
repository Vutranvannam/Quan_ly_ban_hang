import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

import FloatingInput from "../../components/auth/AuthFloatingInput";
import AuthCheckBox from "../../components/auth/AuthCheckBox";
import AuthButton from "../../components/auth/AuthButton";
import AuthLayout from "../../layouts/AuthLayout";
import AuthFooter from "../../components/auth/AuthFooter";
import AuthLink from "../../components/auth/AuthLink";
import AuthMessage from "../../components/auth/AuthMessage";

function PagesLogin() {
  const navigate = useNavigate();
  const {login} = useAuth();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [remember, setRemember] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError("Username/Password không được để trống");
      alert("Username/Password không được để trống");
      return;
    }
    try {
      await login(username, password, remember);
      navigate("/");
    } catch (err) {
      setError(err.message);
      alert(err.message);
    }
  };
  
  return (
    <AuthLayout>

      <AuthMessage title="Chào mừng trở lại!" note="Đăng nhập vào tài khoản của bạn" titleClass="mb-2" noteClass=" " />

      <form onSubmit={handleSubmit}>
        <div className="row">

          <FloatingInput type="text" label="UserName" value={username} onChange={(e) => setUsername(e.target.value)} />
          <FloatingInput type="password" label="Mật khẩu" value={password} onChange={(e) => setPassword(e.target.value)} />

          <AuthCheckBox id="remember" label="Nhớ tài khoản" labelClass="custom-control-label control-label-1" checked={remember} onChange={(e) => setRemember(e.target.checked)}/>

          <AuthLink href="/auth/recover-password" text="Quên mật khẩu?" />

        </div>

        <AuthButton text="Đăng nhập" />
        <AuthFooter text="Tạo tài khoản mới" linkText="Đăng ký" href="/auth/sign-up" />

      </form>

    </AuthLayout>
  );
}

export default PagesLogin;