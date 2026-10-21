export const handleApiError = (error) => {
    const data = error.response?.data;

    if (!error.response) {
        return {
            status: 0,
            code: "NETWORK_ERROR",
            message: "Không kết nối được server"
        };
    }

    return {
        status: error.response.status,
        code: data?.code || "UNKNOWN_ERROR",
        message: data?.message || error.response.statusText || "Lỗi không xác định"
    };
};