import { useState, useEffect } from "react";
import { addProductService } from "../../services/productService";
import { getCategoryListWithDeletedAtIsNullService } from "../../services/categoryService";
import { getUnitListService } from "../../services/unitService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import SelectField from "../../components/addComponent/SelectField";
import CreatableSelectField from "../../components/addComponent/CreatableSelectField";
import FileField from "../../components/addComponent/FileField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

export default function AddProductPage() {

    const [categories, setCategories] = useState([]);
    const [units, setUnits] = useState([]);
    const status = [
        { value: 1, label: "Hoạt động" },
        { value: 0, label: "Bị khóa" }
    ];

    const [sku, setSku] = useState("");
    const [name, setName] = useState("");
    const [selectCategoryId, setSelectCategoryId] = useState(null);
    const [selectUnit, setSelectUnit] = useState(null);
    const [price, setPrice] = useState("");
    const [selectStatus, setSelectStatus] = useState(null);
    const [image, setImage] = useState(null);
    const [description, setDescription] = useState("");

    const handleAddProduct = async (e) => {
        e.preventDefault();

        if (!selectCategoryId || !selectStatus || !selectUnit) {
            alert("Vui lòng nhập đầy đủ dữ liệu");
            return;
        }

        if (!image) {
            alert("Ảnh sản phẩm không được để trống");
            return;
        }

        try {
            const formData = new FormData();

            formData.append("sku", sku);
            formData.append("name", name);
            formData.append("categoryId", selectCategoryId);
            formData.append("unit", selectUnit);
            formData.append("price", price);
            formData.append("status", selectStatus);
            formData.append("description", description);

            if (image) {
                formData.append("image", image);
            }

            await addProductService(formData);

            alert("Thêm sản phẩm thành công!");
            resetForm();

        } catch (err) {
            console.error("❌ ERROR:", err);
            alert(err.response?.data?.message || err.message);
        }
    };
    const resetForm = () => {
        setSku("");
        setName("");
        setPrice("");
        setImage(null);
        setDescription("");
    }

    useEffect(() => {

        getCategoryListWithDeletedAtIsNullService().then(result => {
            const categoryOptions = result.map(category => ({
                value: category.categoryId,
                label: category.name
            }));
            setCategories(categoryOptions);
        });

        getUnitListService().then(result => {
            const unitOptions = result.map(unit => ({
                value: unit.unitId,
                label: unit.name
            }));
            setUnits(unitOptions);
        });
    }, []);

    return (

        <HomeLayout>

            <FormCard title="Thêm sản phẩm">

                <form onSubmit={handleAddProduct}>

                    <div className="row">

                        <InputField
                            label="SKU *"
                            placeholder="Nhập mã sản phẩm"
                            required={true}
                            value={sku}
                            onChange={(e) => setSku(e.target.value)}
                            col="col-md-6"
                        />

                        <InputField
                            label="Tên sản phẩm *"
                            placeholder="Nhập tên sản phẩm"
                            required={true}
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            col="col-md-6"
                        />

                        <SelectField
                            label="Loại sản phẩm *"
                            options={categories}
                            required={true}
                            value={selectCategoryId}
                            onChange={setSelectCategoryId}
                            col="col-md-6"
                        />

                        <CreatableSelectField
                            label="Đơn vị *"
                            options={units}
                            required={true}
                            value={selectUnit}
                            onChange={setSelectUnit}
                            col="col-md-6"
                        />

                        <InputField
                            label="Giá bán*"
                            placeholder="Nhập giá bán"
                            type="number"
                            required={true}
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            col="col-md-6"
                        />

                        <SelectField
                            label="Trạng thái *"
                            options={status}
                            required={true}
                            value={selectStatus}
                            onChange={setSelectStatus}
                            col="col-md-6"
                        />

                        <FileField 
                            label="Ảnh sản phẩm *"
                            onChange={setImage}
                            required={true}
                        />

                        <TextAreaField
                            label="Mô tả sản phẩm"
                            placeholder="Nhập mô tả sản phẩm"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />

                    </div>

                    <FormButtons submitText="Thêm sản phẩm" addReset={resetForm}/>

                </form>

            </FormCard>

        </HomeLayout>

    );
}