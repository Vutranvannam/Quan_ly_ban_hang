import { privateAxios } from "../api/privateAxiosCilent";

export const getCustomerListService = async(page, size, keyword, signal) => {
    const res = await privateAxios.get(
        "/customer/list",
        {
            params: {page, size, keyword},
            signal
        }
    );

    return res.data;
}

export const getCustomerService = async (customerId) => {
    const res = await privateAxios.get(`/customer/${customerId}`);
    return res.data;
}


export const addCustomerService = async (data, signal) => {
    const res = await privateAxios.post(
        "/customer/add", 
        data
    );
};

export const updateCustomerService = async (customerId, data) => {
    const res = await privateAxios.patch(
        `/customer/${customerId}`,
        data
    );

    return res.data;
};

export const deleteCustomerService = async (customerId) => {
    await privateAxios.delete(`/customer/${customerId}`);
};

export const exportExcelService = async (keyword, signal) => {
    const res = await privateAxios.get(
        "/customer/list/export-excel",
        {
            params: {page: 0, size: 10, keyword},
            responseType: "blob",
            signal
        }
    );

    const url = window.URL.createObjectURL(new Blob([res.data]));

    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "customers.xlsx");

    document.body.appendChild(link);
    link.click();
    link.remove();
};