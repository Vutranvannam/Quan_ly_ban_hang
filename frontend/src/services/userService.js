import { privateAxios } from "../api/privateAxiosCilent";

export const getUserListService = async(page, size, keyword, signal) => {
    const res = await privateAxios.get(
        "/user",
        {
            params: {page, size, keyword},
            signal
        }
    );

    return res.data;
}

export const getUserService = async (userId) => {
    const res = await privateAxios.get(`/user/${userId}`);
    return res.data;
}

export const addUserService = async (username, email, fullName, selectedRoles, selectedStatus) => {
    await privateAxios.post(
        "/user",
        {
            username,
            email,
            fullName,
            selectedRoles,
            selectedStatus
        }
    );
};

export const updateUserService = async (userId, data) => {
    const res = await privateAxios.patch(
        `/user/${userId}`,
        data
    );

    return res.data;
};

export const deleteUserService = async (userId) => {
    await privateAxios.delete(`/user/${userId}`);
};