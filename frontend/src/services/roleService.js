import { privateAxios } from "../api/privateAxiosCilent";

export const getRoleListWithDeletedAtIsNullService = async() => {
    const res = await privateAxios.get(
        "/role/list"
    );
    return res.data;
}