import { publicAxios } from "../api/publicAxiosCilent";

export const loginService = async (username, password) => {
    const res = await publicAxios.post(
        "auth/login", 
        { 
            username, 
            password 
        }
    );
    
    const auth = res.data;
    console.log(auth);

    localStorage.setItem(
        "auth", 
        JSON.stringify(res.data)
    );

    return auth;
}

export const logoutService = async () => {
    await publicAxios.post(
        "auth/logout",
        {},
        {withCredentials: true}
    );

    localStorage.removeItem("auth");

}

export const registerService = async (data) => {
        await publicAxios.post(
            "auth/register", 
            data
        );

}

export const recoverPasswordService = async (email) => {

    await publicAxios.post(
        "auth/recover-password", 
        {
            email
        }
    );
}

export const refreshTokenService = async () => {
    const res = await publicAxios.post(
        "auth/refresh-token",
        {}, 
        {withCredentials: true}
    );

    const auth = res.data;
    console.log(auth);

    localStorage.setItem(
        "auth", 
        JSON.stringify(res.data)
    );
    return res.data;
}    