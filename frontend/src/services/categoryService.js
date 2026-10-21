import { privateAxios } from "../api/privateAxiosCilent";

export const getCategoryListService = async(page, size, keyword, signal) => {
    const res = await privateAxios.get(
        "/category/list",
        {
            params: {page, size, keyword},
            signal
        }
    );

    return res.data;
};

export const getCategoryService = async (categoryId) => {
    const res = await privateAxios.get(`/category/${categoryId}`);
    return res.data;
}

export const addCategoryService = async (data) => {
    const res = await privateAxios.post(
        "/category/add", 
        data
    );
};

export const updateCategoryService = async (categoryId, data) => {
    const res = await privateAxios.patch(
        `/category/${categoryId}`,
        data
    );

    return res.data;
};

export const exportExcelService = async (keyword, signal) => {
    const res = await privateAxios.get(
        "/category/list/export-excel",
        {
            params: {page: 0, size: 10, keyword},
            responseType: "blob",
            signal
        }
    );

    const url = window.URL.createObjectURL(new Blob([res.data]));

    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "categories.xlsx");

    document.body.appendChild(link);
    link.click();
    link.remove();
};

export const getCategoryListWithDeletedAtIsNullService = async() => {
    const res = await privateAxios.get(
        "/category/list-all"
    );
    return res.data;
}

export const deleteCategoryService = async (categoryId) => {
    await privateAxios.delete(`/category/${categoryId}`);
};