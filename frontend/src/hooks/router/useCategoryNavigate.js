import { useNavigate } from "react-router-dom";

export default function useCategoryNavigate() {
    const navigate = useNavigate();

    const goToAddCategory = () => {
        navigate("/category/add");
    }

    const goToUpdateCategory = (categoryId) => {
        navigate(`/category/update/${categoryId}`);
    }

    return {
        goToAddCategory,
        goToUpdateCategory,
    };
}