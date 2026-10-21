import { deleteUserService } from "../../../services/userService";

export default function useDeleteUser(onSuccess) {

    const handleDelete = async (userId) => {

        const isConfirm = window.confirm(
            "Bạn có chắc chắn muốn xóa user này không?"
        );

        if (!isConfirm) return;

        try {

            await deleteUserService(userId);
            onSuccess?.();

        } catch (err) {
            alert(err.message);
        }
    };

    return {
        handleDelete,
    };
}