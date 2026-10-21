import { addCategoryService } from "../../../services/categoryService";

export default function useAddCategory(onSuccess) {
    const handleAddCategory = async (
        e,
        formData
    ) => {
        e.preventDefault();
        try {
            const res = await addCategoryService(
                formData.name,
                formData.description
            );
            alert("Thêm loại linh kiện thành công!");
            onSuccess?.();
            
        } catch (err) {

            const data = err.response?.data;
            alert(data?.message || "Thêm loại linh kiện thất bại!");
        }
    }
    return {
        handleAddCategory,
    }
}