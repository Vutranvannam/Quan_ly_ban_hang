
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getProductService, updateProductService } from "../../services/productService";
import { getCategoryListWithDeletedAtIsNullService } from "../../services/categoryService";
import { getUnitListService } from "../../services/unitService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import SelectField from "../../components/addComponent/SelectField";
import CreatableSelectField from "../../components/addComponent/CreatableSelectField";
import ShowImageFileField from "../../components/addComponent/ShowImageFileField";
import FileField from "../../components/addComponent/FileField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

import { formatDate } from "../../utils/date";

export default function UpdateProductPage() {

    const {productId} = useParams();

    const [categories, setCategories] = useState([]);
    const [units, setUnits] = useState([]);
    const status = [
        { value: true, label: "Hoạt động" },
        { value: false, label: "Bị khóa" }
    ];

    const [sku, setSku] = useState("");
    const [name, setName] = useState("");
    const [selectCategoryId, setSelectCategoryId] = useState(null);
    const [selectUnit, setSelectUnit] = useState(null);
    const [price, setPrice] = useState("");
    const [selectStatus, setSelectStatus] = useState(null);
    const [image, setImage] = useState(null);
    const [previewUrl, setPreviewUrl] = useState("");
    const [description, setDescription] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    const [updatedAt, setUpdatedAt] = useState("");

    const [product, setProduct] = useState({});


    const handleSubmit = async (e) => {

    e.preventDefault();

    try {

        const formData = new FormData();
        
        if (name !== product.name) formData.append("name", name);

        const category = categories.find(
            category => category.value === selectCategoryId
        );
        const categoryName = category?.label;
        if (categoryName !== product.categoryName) formData.append("categoryId", selectCategoryId);

        if (selectUnit !== product.unitName) formData.append("unit", selectUnit);
        if (description !== product.description) formData.append("description", description);
        if (Number(price) !== Number(product.price)) formData.append("price", price);
        if (selectStatus !== product.active) formData.append("active", selectStatus);
        if (image instanceof File) formData.append("image", image);

        if ([...formData.keys()].length === 0) {

            alert("Không có thay đổi!");
            return;
        }   

        const res = await updateProductService(
            productId,
            formData
        );

        fillForm(res, categories, units);
        setProduct(res);

        alert("Chỉnh sửa loại linh kiện thành công!");

    } catch (err) {

        console.error("UPDATE ERROR:", err);

        fillForm(product, categories, units);

        const data = err.response?.data;

        alert(data?.message || "Chỉnh sửa loại linh kiện thất bại!");
    }
};

    const fillForm = (product, categories, units) => {
        setSku(product.sku);
        setName(product.name);

        const category = categories.find(category => category.label === product.categoryName);
        setSelectCategoryId(category.value);

        const unit = units.find(unit => unit.label === product.unitName);
        setSelectUnit(unit.label);

        setPrice(product.price);
        setSelectStatus(product.active);
        setImage(product.productImages[0]);
        setPreviewUrl(product.productImages[0].imageUrl);
        setDescription(product.description);
        setCreatedAt(formatDate(product.createdAt));
        setUpdatedAt(formatDate(product.updatedAt));
    };

    const resetForm = (categoryOptions, unitOptions) => {
        getProductService(productId).then(res => {
            fillForm(res, categoryOptions, unitOptions);
            setProduct(res);
        });
    };

    useEffect(() => {

        getCategoryListWithDeletedAtIsNullService().then(result => {
            const categoryOptions = result.map(category => ({
                value: category.categoryId,
                label: category.name
            }));
            setCategories(categoryOptions);

            getUnitListService().then(result => {
                const unitOptions = result.map(unit => ({
                    value: unit.unitId,
                    label: unit.name
                }));
                setUnits(unitOptions);

                resetForm(categoryOptions, unitOptions);

            });
        });

    }, [productId]);
    return (

        <HomeLayout>

            <FormCard title="Chỉnh sửa loại linh kiện">

                <form onSubmit={handleSubmit}>

                    <div className="row">

                         <InputField
                            label="SKU *"
                            required={true}
                            value={sku}
                            readOnly={true}
                            onChange={(e) => setSku(e.target.value)}
                            col="col-md-6"
                        />

                        <InputField
                            label="Tên sản phẩm *"
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
                            label="Giá bán"
                            type="number"
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

                        <ShowImageFileField
                            label="Ảnh sản phẩm *"
                            imageUrl={previewUrl}
                            onChange={(file) => {

                                setImage(file);

                                if(file) {
                                    setPreviewUrl(URL.createObjectURL(file));
                                }
                            }}
                        />
                        
                        <TextAreaField
                            label="Mô tả sản phẩm"
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

                    <FormButtons submitText="Xác nhận" addReset={() => resetForm(categories, units)}/>

                </form>

            </FormCard>

        </HomeLayout>

    );

}