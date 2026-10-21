import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { recoverPasswordService } from "../../services/authService";

import FloatingInput from "../../components/auth/AuthFloatingInput";
import AuthButton from "../../components/auth/AuthButton";
import AuthLayout from "../../layouts/AuthLayout";
import AuthMessage from "../../components/auth/AuthMessage";
import AuthFooter from "../../components/auth/AuthFooter";

function PagesRecoverPassword() {

  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await recoverPasswordService(email);
      navigate("/auth/confirm-mail");
    } catch (err) {
      const data = err.response?.data;
    } finally{
      setLoading(false);
    }
  };

  return (
    <AuthLayout>
      
      <AuthMessage title="Khôi phục mật khẩu" note="Nhập email của bạn để nhận hướng dẫn khôi phục mật khẩu." titleClass="mb-2" noteClass=" "/>

      <form onSubmit={handleSubmit}>
        <div className="row">
          <FloatingInput type="email" label="Email" value={email} onChange={(e) => setEmail(e.target.value)}/>
        </div>

        <AuthButton text="Khôi phục mật khẩu" />
        <AuthFooter text="Đã có tài khoản?" linkText="Đăng nhập" href="/auth/" />
      </form>

    </AuthLayout>
  );
}

export default PagesRecoverPassword;
