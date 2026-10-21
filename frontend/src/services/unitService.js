import { privateAxios } from "../api/privateAxiosCilent";

export const getUnitListService = async() => {
    const res = await privateAxios.get(
        "/unit/list"
    );
    return res.data;
}