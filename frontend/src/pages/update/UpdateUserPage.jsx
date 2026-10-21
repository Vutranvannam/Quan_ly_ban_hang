import { useEffect} from "react";
import { useParams } from "react-router-dom";

import useRoles from "../../hooks/api/role/useRoles";

import useUser from "../../hooks/api/user/useUser";

import useUpdateUser from "../../hooks/api/user/useUpdateUser";

import useUpdateUserForm from "../../hooks/form/user/useUpdateUserForm";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import SelectField from "../../components/addComponent/SelectField";
import MultiSelectField from "../../components/addComponent/MultiSelectField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

import { formatDate } from "../../utils/date";

export default function UpdateUserPage() {

    const { userId } = useParams();

    const status = [
        { value: true, label: "Hoạt động" },
        { value: false, label: "Bị khóa" }
    ];

    const lockedStatus = [
        { value: false, label: "Không khóa" },
        { value: true, label: "Bị khóa" }
    ];

    const { roles } = useRoles();

    const {
        user,
        fetchUser,
    } = useUser(userId);


    const {

        username,

        email,
        setEmail,

        fullName,
        setFullName,

        selectedRoles,
        setSelectedRoles,

        selectedStatus,
        setSelectedStatus,

        locked,
        setLocked,

        createdAt,
        updatedAt,

        fillForm,

    } = useUpdateUserForm();

    useEffect(() => {

        if (user && roles.length > 0) {
            fillForm(user, roles);
        }

    }, [user, roles]);

    const { handleUpdateUser } =
        useUpdateUser(
            user,
            {
                email,
                fullName,
                selectedRoles,
                selectedStatus,
                locked,
            },
            (res) => {
                fillForm(res, roles);
            },
            () => {
                fillForm(user, roles);
            }
        );

    return (

        <HomeLayout>

            <FormCard title="Chỉnh sửa thông tin người dùng">

                <form 
                    onSubmit={(e) =>
                        handleUpdateUser(e, userId)
                    }
                >

                    <div className="row">

                        <InputField
                            label="Username"
                            col="col-md-6"
                            value={username}
                            readOnly={true}
                        />

                        <InputField
                            label="Email"
                            placeholder="Nhập email"
                            col="col-md-6"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <InputField
                            label="Họ và tên"
                            placeholder="Nhập họ và tên"
                            col="col-md-6"
                            value={fullName}
                            onChange={(e) => setFullName(e.target.value)}
                        />

                        <SelectField
                            label="Trạng thái"
                            options={status}
                            value={selectedStatus}
                            onChange={setSelectedStatus}
                            col="col-md-6"
                        />

                        <SelectField
                            label="Khóa tài khoản"
                            options={lockedStatus}
                            value={locked}
                            onChange={setLocked}
                            col="col-md-6"
                        />

                        <MultiSelectField
                            label="Vai trò"
                            options={roles}
                            value={selectedRoles}
                            onChange={setSelectedRoles}
                            col="col-md-6"
                        />

                        <InputField
                            label="Ngày tạo"
                            col="col-md-6"
                            value={createdAt || ""}
                            readOnly={true}
                        />

                        <InputField
                            label="Ngày cập nhật"
                            col="col-md-6"
                            value={updatedAt || ""}
                            readOnly={true}
                        />

                    </div>

                    <FormButtons submitText="Xác nhận chỉnh sửa" addReset={() => fillForm(user, roles)}/>

                </form>

            </FormCard>

        </HomeLayout>

    );
}