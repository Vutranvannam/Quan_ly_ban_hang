import { addUserService } from "../../../services/userService";

export default function useAddUser(onSuccess) {

    const handleAddUser = async (
        e,
        formData
    ) => {

        e.preventDefault();

        try {

            await addUserService(
                formData.username,
                formData.email,
                formData.fullName,
                formData.selectedRoles,
                formData.selectedStatus
            );

            alert("Thêm người dùng thành công!");

            onSuccess?.();

        } catch (err) {

            const data = err.response?.data;

            alert(
                data?.message ||
                "Thêm người dùng thất bại!"
            );
        }
    };

    return {
        handleAddUser,
    };
}