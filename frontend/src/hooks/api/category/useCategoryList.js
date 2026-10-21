import { useEffect, useState } from "react";

import { getCategoryListService } from "../../../services/categoryService";

import useAbortController from "../../utils/useAbortController";

export default function useCategoryList(page, size, keyword, onSuccess) {

    const { createSignal } = useAbortController();

    const [categories, setCategories] = useState([]);

    const fetchCategories = async () => {
        try {
            const signal = createSignal();

            const res = await getCategoryListService(
                page, 
                size, 
                keyword, 
                signal
            );

            setCategories(res.content);

            onSuccess(res);
        } catch (err) {
            if (err.name !== "CanceledError") {
                console.log(err);
            }
        }
    }

    useEffect(() => {
        fetchCategories();
    }, [page, size, keyword])

    return { 
        categories,
        fetchCategories
    };
}