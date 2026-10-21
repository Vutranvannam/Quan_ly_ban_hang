import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function PrivateRoute({ children }) {
    const { auth, loading } = useAuth();
    if(loading) return null;
    return auth ? children : <Navigate to="/auth/sign-in" />;
}