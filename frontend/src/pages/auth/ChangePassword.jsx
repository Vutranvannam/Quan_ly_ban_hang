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

function PageChangePassword() {
    const  {auth} = useAuth();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    return (
        <AuthLayout>
            <img src="../assets/images/user/1.png" className="rounded avatar-80 mb-3" alt=""></img>
            <AuthMessage title={`Xin chào ${auth?.user?.fullName}`} note="Hãy đổi mật khẩu trước khi đăng nhập" titleClass="mb-2" noteClass=" " />
            <form>
                <div className="row">

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
                    <AuthButton text="Xác nhận"/>
                </div>
            </form>    
        </AuthLayout>
    )
}

export default PageChangePassword