export const formatDate = (dateStr) => {
    if (!dateStr) return "-";

    return new Intl.DateTimeFormat("vi-VN").format(new Date(dateStr));
};