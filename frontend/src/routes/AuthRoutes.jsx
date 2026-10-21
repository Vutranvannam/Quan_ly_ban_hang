import { Route } from "react-router-dom";

import SignInPage from "../pages/auth/SignInPage";
import SignUpPage from "../pages/auth/SignUpPage";
import RecoverPasswordPage from "../pages/auth/RecoverPasswordPage";
import ConfirmMailPage from "../pages/auth/ConfirmMailPage";
import ChangePasswordPage from "../pages/auth/ChangePassword";

function AuthRoutes() {
  return (
    <>
      <Route path="/auth/sign-in" element={<SignInPage />} />
      <Route path="/auth/sign-up" element={<SignUpPage />} />
      <Route path="/auth/recover-password" element={<RecoverPasswordPage />} />
      <Route path="/auth/confirm-mail" element={<ConfirmMailPage />} />
      <Route path="/auth/change-password" element={<ChangePasswordPage />} />
    </>
  );
}

export default AuthRoutes;