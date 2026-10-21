
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getCategoryService, updateCategoryService } from "../../services/categoryService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

import { formatDate } from "../../utils/date";

export default function UpdateCategoryPage() {

    const {categoryId} = useParams();

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    const [updatedAt, setUpdatedAt] = useState("");

    const [category, setCategory] = useState({});

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            const payload = {};
            if(description !== category.description) payload.description = description;

            if (Object.keys(payload).length === 0) {
                alert("Không có thay đổi!");
                return;
            }

            const res = await updateCategoryService(
                categoryId,
                payload
            );

            fillForm(res);
            setCategory(res);

            alert("Chỉnh sửa loại linh kiện thành công!");


        } catch (err) {

            fillForm(category);

            const data = err.response?.data;
            alert(data?.message || "Chỉnh sửa loại linh kiện thất bại!");
        }
    };

    const fillForm = (category) => {
        setDescription(category.description);
        setCreatedAt(formatDate(category.createdAt));
        setUpdatedAt(formatDate(category.updatedAt));
    };

    const resetForm = () => {
        getCategoryService(categoryId).then(res => {
            fillForm(res);
            setCategory(res);
        });
    };

    useEffect(() => {
        resetForm();
    }, [categoryId]);
    return (

        <HomeLayout>

            <FormCard title="Chỉnh sửa loại linh kiện">

                <form onSubmit={handleSubmit}>

                    <div className="row">

                        <InputField 
                            label="Tên loại linh kiện *" 
                            placeholder="Nhập tên loại linh kiện" 
                            required={true} 
                            value={name} 
                            onChange={(e) => setName(e.target.value)}
                            readOnly={true}
                        />

                        <TextAreaField 
                            label="Mô tả" 
                            placeholder="Nhập mô tả loại linh kiện " 
                            value={description} 
                            onChange={(e) => setDescription(e.target.value)}
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

                    <FormButtons submitText="Xác nhận" addReset={resetForm}/>

                </form>

            </FormCard>

        </HomeLayout>

    );

}