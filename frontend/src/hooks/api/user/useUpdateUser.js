import { updateUserService } from "../../../services/userService";

import isSameRoles from "../../../utils/isSameRoles";

export default function useUpdateUser(
    user,
    formData,
    onSuccess,
    onError
) {

    const handleUpdateUser = async (
        e,
        userId
    ) => {

        e.preventDefault();

        try {

            const payload = {};

            if (formData.email !== user.email) {
                payload.email = formData.email;
            }

            if (formData.fullName !== user.fullName) {
                payload.fullName = formData.fullName;
            }

            if (!isSameRoles(formData.selectedRoles, user.roles)) {
                payload.roleIds =
                    formData.selectedRoles;
            }

            if ( formData.selectedStatus !== user.enabled ) {
                payload.status =
                    formData.selectedStatus;
            }

            if ( formData.locked !== user.locked ) {
                payload.locked =
                    formData.locked;
            }

            if ( Object.keys(payload).length === 0 ) {
                alert("Không có thay đổi!");
                return;
            }

            const res = await updateUserService(
                userId,
                payload
            );

            alert("Chỉnh sửa thông tin người dùng thành công!");

            onSuccess?.(res);

        } catch (err) {

            onError?.();

            const data = err.response?.data;

            alert(
                data?.message ||
                "Chỉnh sửa thông tin người dùng thất bại!"
            );
        }
    };

    return {
        handleUpdateUser,
    };
}