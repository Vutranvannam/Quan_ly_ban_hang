import { createContext, useContext, useState, useEffect } from "react";
import { loginService, logoutService, registerService, refreshTokenService } from "../services/authService";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [auth, setAuth] = useState(() => {
        return JSON.parse(localStorage.getItem("auth") || "null");
    }); // 👈 state auth
    const [loading, setLoading] = useState(true);

    useEffect(() => {
       initAuth();
    }, []);


    const login = async (username, password, remember) => {
        const authResponse = await loginService(username, password);

        setAuth(authResponse);
        if (remember) localStorage.setItem("auth", JSON.stringify(authResponse));
        else localStorage.removeItem("auth");
    };

    
    const logout = async () => {
        try {
            await logoutService();
        } catch (error) {
            console.log(error);
        }
        
        setAuth(null);
        localStorage.removeItem("auth");
    };

    const register = async (data) => {
        await registerService(data);
    };

    const initAuth = async () => {
        const auth = localStorage.getItem("auth");
        if (!auth) {
            setLoading(false);
            return; // chưa login → không gọi refresh token
        }
        setAuth(JSON.parse(auth));

        try {
            const res = await refreshTokenService();
            setAuth(res);
            localStorage.setItem("auth", JSON.stringify(res));
        } catch (error) {
            console.log("Refresh token failed", error.message);
            setAuth(null);
            localStorage.removeItem("auth");
        }
        setLoading(false);
    }

    return (
        <AuthContext.Provider value={{ auth, loading, setAuth, login, logout, register }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);