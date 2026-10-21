import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getSupplierService, updateSupplierService } from "../../services/supplierService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

import { formatDate } from "../../utils/date";

export default function UpdateSupplierPage() {

    const { supplierId } = useParams();

    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [createdAt, setCreatedAt] = useState('');
    const [updatedAt, setUpdatedAt] = useState('');

    const [supplier, setSupplier] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();  

        if (!/^0\d{9,10}$/.test(phone)) {
            alert("Số điện thoại không hợp lệ!");
            return;
        }

        try{

            const payload = {};
            if(name !== supplier.name) payload.name = name;
            if(phone !== supplier.phone) payload.phone = phone;
            if(email !== supplier.email) payload.email = email;
            if(address !== supplier.address) payload.address = address;
            if(Object.keys(payload).length === 0) {
                alert("Không có thay đổi!");
                return;
            }

            const res = await updateSupplierService(
                supplierId,
                payload

            );

            fillForm(res);
            setSupplier(res);
            
            alert("Chỉnh sửa nhà cung cấp thành công!");

        } catch (err) {

            fillForm(supplier);

            const data = err.response?.data;
            alert(data?.message || "Chỉnh sửa nhà cung cấp thất bại!");
        }
    }
    
    const resetForm = () => {
        getSupplierService(supplierId).then(res => {
            fillForm(res);
            setSupplier(res);
        });
    }

    const fillForm = (supplier) => {

        setName(supplier.name);
        setPhone(supplier.phone);
        setEmail(supplier.email);
        setAddress(supplier.address);
        setCreatedAt(formatDate(supplier.createdAt));
        setUpdatedAt(formatDate(supplier.updatedAt));
    };
    
    useEffect(() => {
        resetForm();
    }, [supplierId]);

    return (

        <HomeLayout>

            <FormCard title="Chỉnh sửa nhà cung cấp">

                <form onSubmit={handleSubmit}>

                    <div className="row">

                        <InputField
                            label="Tên nhà cung cấp *"
                            placeholder="Nhập tên nhà cung cấp"
                            required={true}
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />

                        <InputField
                            label="Số điện thoại *"
                            placeholder="Nhập số điện thoại"
                            required={true}
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                        />

                        <InputField
                            label="Email *"
                            placeholder="Nhập email"
                            required={true}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <TextAreaField
                            label="Địa chỉ *"
                            placeholder="Nhập địa chỉ"
                            required={true}
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
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