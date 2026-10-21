import { deleteCategoryService } from "../../../services/categoryService";

export default function useDeleteCategory(onSuccess) {
    const handleDelete = async (categoryId) => {

        const isConfirm = window.confirm(
            "Bạn có chắc chắn muốn xóa category này không?"
        );

        if (!isConfirm) return;

        try {
            await deleteCategoryService(categoryId);
            
            onSuccess?.();
        } catch (err) {
            alert(err.message);
        }
    }

    return {
        handleDelete,
    }
}

