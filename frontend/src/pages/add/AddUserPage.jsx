import useRoles from "../../hooks/api/role/useRoles";
import useAddUser from "../../hooks/api/user/useAddUser";

import useUserForm from "../../hooks/form/user/useAddUserForm";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import SelectField from "../../components/addComponent/SelectField";
import MultiSelectField from "../../components/addComponent/MultiSelectField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

export default function AddUserPage() {

    const status = [
        { value: true, label: "Hoạt động" },
        { value: false, label: "Bị khóa" }
    ];

    const { roles } = useRoles();

    const {

        username,
        setUsername,

        email,
        setEmail,

        fullName,
        setFullName,

        selectedRoles,
        setSelectedRoles,

        selectedStatus,
        setSelectedStatus,

        resetForm,

    } = useUserForm();
    const { handleAddUser } = useAddUser(resetForm);

    return (

        <HomeLayout>
            <FormCard title="Thêm người dùng">
                <form
                    onSubmit={(e) =>
                        handleAddUser(e, {
                            username,
                            email,
                            fullName,
                            selectedRoles,
                            selectedStatus
                        })
                    }
                >

                    <div className="row">

                        <InputField
                            label="Username *"
                            placeholder="Nhập username"
                            required
                            col="col-md-6"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />

                        <InputField
                            label="Email *"
                            placeholder="Nhập email"
                            required
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

                        <MultiSelectField
                            label="Vai trò"
                            options={roles}
                            value={selectedRoles}
                            onChange={setSelectedRoles}
                        />

                    </div>

                    <FormButtons
                        submitText="Thêm người dùng"
                        addReset={resetForm}
                    />
                    
                </form>
            </FormCard>
        </HomeLayout>
    );
}