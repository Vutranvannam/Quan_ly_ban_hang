    import axios from "axios";
    import { getAccessToken } from "../api/tokenService";
    import { handleApiError } from "../utils/errorHandler";
    import { refreshTokenService } from "../services/authService";


    let isRefreshing = false;
    let pendingQueue = [];

    export const privateAxios = axios.create({
        baseURL: "http://localhost:8080/api/",
        withCredentials: true,
    });

    privateAxios.interceptors.request.use((config) => {
        const token = getAccessToken();

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });

    privateAxios.interceptors.response.use(
        (response) => response, 
        async (error) => {
            const originalRequest = error.config;

            if(error.response.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;

                if(isRefreshing) {
                    return new Promise((resolve) => {
                        pendingQueue.push((token) => {
                            originalRequest.headers.Authorization = `Bearer ${token}`;
                            resolve(privateAxios(originalRequest));
                        })
                    })
                }

                isRefreshing = true;

                try {
                    const newAccessToken = await refreshTokenService();

                    pendingQueue.forEach((callback) => callback(newAccessToken));
                    pendingQueue = [];

                    return privateAxios(originalRequest);
                } catch (error) {
                    localStorage.removeItem("auth");
                    window.location.href = "/auth/sign-in";
                    return Promise.reject(error);
                } finally {
                    isRefreshing = false;
                }
            }
            return Promise.reject(handleApiError(error));
        }
    );