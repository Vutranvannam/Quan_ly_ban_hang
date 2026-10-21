import { useEffect, useState } from "react";

import { getCategoryService } from "../../../services/categoryService";

export default function useCategory(categoryId) {

    const [category, setCategory] = useState({});    

    const fetchCategory = async (categoryId) => {
        const res = await getCategoryService(categoryId);
        return res;
    };

    useEffect(() => {
       
    }, [categoryId]);

    return ({
        category,   
    })
}