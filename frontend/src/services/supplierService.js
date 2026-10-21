import { privateAxios } from "../api/privateAxiosCilent";

export const getSupplierListService = async(page, size, keyword, signal) => {
    const res = await privateAxios.get(
        "/supplier/list",
        {
            params: {page, size, keyword},
            signal
        }
    );

    return res.data;
}

export const getSupplierService = async (supplierId) => {
    const res = await privateAxios.get(`/supplier/${supplierId}`);
    return res.data;
}

export const addSupplierService = async (data, signal) => {
    const res = await privateAxios.post(
        "/supplier/add", 
        data
    );
};

export const updateSupplierService = async (supplierId, data) => {
    const res = await privateAxios.patch(
        `/supplier/${supplierId}`,
        data
    );

    return res.data;
};

export const deleteSupplierService = async (supplierId) => {
    await privateAxios.delete(`/supplier/${supplierId}`);
};

export const exportExcelService = async (keyword, signal) => {
    const res = await privateAxios.get(
        "/supplier/list/export-excel",
        {
            params: {page: 0, size: 10, keyword},
            responseType: "blob",
            signal
        }
    );

    const url = window.URL.createObjectURL(new Blob([res.data]));

    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "suppliers.xlsx");

    document.body.appendChild(link);
    link.click();
    link.remove();
};