import { useState } from "react";

export default function usePagination(defaultPage = 0, defaultSize = 10) {

    const [page, setPage] = useState(defaultPage);
    const [size, setSize] = useState(defaultSize);

    const [total, setTotal] = useState(0);
    const [totalPage, setTotalPage] = useState(0);

    return {
        page,
        size,
        total,
        totalPage,

        setPage,
        setSize,
        setTotal,
        setTotalPage,
    };
}