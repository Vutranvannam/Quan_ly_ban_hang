import { useState, useRef } from "react";
import { addSupplierService } from "../../services/supplierService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

export default function AddSupplierPage() {

    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();  

        if (!/^0\d{9,10}$/.test(phone)) {
            alert("Số điện thoại không hợp lệ!");
            return;
        }

        try{
            const res = await addSupplierService(
                {
                    name,
                    phone,
                    email,
                    address
                },
                controller.signal
            );
            
            alert("Thêm khách hàng thành công!");
            resetForm();

        } catch (err) {
            const data = err.response?.data;
            alert(data?.message || "Thêm khách hàng thất bại!");
        }
    }
    
    const resetForm = () => {
        setName('');
        setPhone('');
        setEmail('');
        setAddress('');
    }
    
    return (

        <HomeLayout>

            <FormCard title="Thêm nhà cung cấp">

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

                    </div>

                    <FormButtons submitText="Thêm nhà cung cấp" addReset={resetForm}/>

                </form>

            </FormCard>

        </HomeLayout>

    );
}