import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getCustomerService, updateCustomerService } from "../../services/customerService";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";
import HomeLayout from "../../layouts/HomeLayout";

import { formatDate } from "../../utils/date";

export default function UpdateCustomerPage() {

    const { customerId } = useParams();

    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [createdAt, setCreatedAt] = useState('');
    const [updatedAt, setUpdatedAt] = useState('');

    const [customer, setCustomer] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();  

        if (!/^0\d{9,10}$/.test(phone)) {
            alert("Số điện thoại không hợp lệ!");
            return;
        }

        try{

            const payload = {};
            if(name !== customer.name) payload.name = name;
            if(phone !== customer.phone) payload.phone = phone;
            if(email !== customer.email) payload.email = email;
            if(address !== customer.address) payload.address = address;
            if(Object.keys(payload).length === 0) {
                alert("Không có thay đổi!");
                return;
            }

            const res = await updateCustomerService(
                customerId,
                payload

            );

            fillForm(res);
            setCustomer(res);
            
            alert("Chỉnh sửa khách hàng thành công!");

        } catch (err) {

            fillForm(customer);

            const data = err.response?.data;
            alert(data?.message || "Chỉnh sửa khách hàng thất bại!");
        }
    }
    
    const resetForm = () => {
        getCustomerService(customerId).then(res => {
            fillForm(res);
            setCustomer(res);
        });
    }

    const fillForm = (customer) => {

        setName(customer.name);
        setPhone(customer.phone);
        setEmail(customer.email);
        setAddress(customer.address);
        setCreatedAt(formatDate(customer.createdAt));
        setUpdatedAt(formatDate(customer.updatedAt));
    };
    
    useEffect(() => {
        resetForm();
    }, [customerId]);

    return (

        <HomeLayout>

            <FormCard title="Chỉnh sửa khách hàng">

                <form onSubmit={handleSubmit}>

                    <div className="row">

                        <InputField
                            label="Tên khách hàng *"
                            placeholder="Nhập tên khách hàng"
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