import { useNavigate } from "react-router-dom";
import AuthButton from "../../components/auth/AuthButton";
import AuthLayout from "../../layouts/AuthLayout";
import AuthMessage from "../../components/auth/AuthMessage";

function ConfirmMail() {

  const navigate = useNavigate();

  const goToSignIn = () => {
    navigate("/auth/sign-in");
  };

  return (
    <AuthLayout>

      <img src="/assets/images/login/mail.png" className="img-fluid" width="80" alt="mail" />

      <AuthMessage
        title="Xác nhận email"
        note="Vui lòng kiểm tra hộp thư của bạn và nhấp vào liên kết xác nhận để kích hoạt tài khoản."
        titleClass="mt-3 mb-0"
        noteClass="cnf-mail mb-1"
      />

      <form>
        <AuthButton text="Trở về trang chủ" onClick={goToSignIn} />
      </form>

    </AuthLayout>
  );
}

export default ConfirmMail;