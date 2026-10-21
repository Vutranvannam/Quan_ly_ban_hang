import { privateAxios } from "../api/privateAxiosCilent";

export const addProductService = async(formData) => {
    await privateAxios.post(
        "/product/add", 
        formData
    );
};

export const updateProductService = async (productId, data) => {
    const res = await privateAxios.patch(
        `/product/${productId}`,
        data
    );

    return res.data;
};


export const deleteProductService = async (productId) => {
    await privateAxios.delete(`/product/${productId}`);
};

export const getProductListService = async(page, size, keyword, signal) => {
    const res = await privateAxios.get(
        "/product/list",
        {
            params: {page, size, keyword},
            signal
        }
    );

    return res.data;
}

export const getProductService = async (productId) => {
    const res = await privateAxios.get(`/product/${productId}`);
    return res.data;
}

export const exportExcelService = async (keyword, signal) => {
    const res = await privateAxios.get(
        "/product/list/export-excel",
        {
            params: {page: 0, size: 10, keyword},
            responseType: "blob",
            signal
        }
    );

    const url = window.URL.createObjectURL(new Blob([res.data]));

    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "products.xlsx");

    document.body.appendChild(link);
    link.click();
    link.remove();
}