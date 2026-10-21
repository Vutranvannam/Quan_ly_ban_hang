
import useAddCategory from "../../hooks/api/category/useAddCategory";
import useAddCategoryForm from "../../hooks/form/category/useAddCategoryForm";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

export default function AddCategoryPage() {

    const { 
        name, 
        setName, 

        description,
        setDescription, 

        resetForm 
    } = useAddCategoryForm();

    const { handleAddCategory } = useAddCategory(resetForm);

    return (

        <HomeLayout>

            <FormCard title="Thêm loại linh kiện">

                <form onSubmit={handleAddCategory}>

                    <div className="row">

                        <InputField 
                            label="Tên loại linh kiện *" 
                            placeholder="Nhập tên loại linh kiện" 
                            required={true} 
                            value={name} 
                            onChange={(e) => setName(e.target.value)}
                        />

                        <TextAreaField 
                            label="Mô tả" 
                            placeholder="Nhập mô tả loại linh kiện " 
                            value={description} 
                            onChange={(e) => setDescription(e.target.value)}
                            />

                    </div>

                    <FormButtons submitText="Thêm loại linh kiện" addReset={resetForm}/>

                </form>

            </FormCard>

        </HomeLayout>

    );

}