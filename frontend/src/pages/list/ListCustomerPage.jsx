import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { getCustomerListService, exportExcelService, deleteCustomerService } from "../../services/customerService";

import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import CustomerRow from "../../components/listdata/table/row/CustomerRow";


export default function ListCustomerPage() {
    const names = ["Tên khách hàng", "SĐT", "Email", "Địa chỉ", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"];

    const [customers, setCustomers] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [total, setTotal] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [keyword, setKeyword] = useState("");

    const controllerRef = useRef(null);
    const navigate = useNavigate();


    const fetchCustomers = async (page, size, keyword = "") => {

        if (controllerRef.current) {
            controllerRef.current.abort();
        }
        const controller = new AbortController();
        controllerRef.current = controller;

        getCustomerListService(page, size, keyword, controller.signal).then(res => {
            setCustomers(res.content);
            setPage(res.page);
            setSize(res.size);
            setTotal(res.total);
            setTotalPage(res.totalPages);
        })
        .catch((err) => {
            if (err.name !== "CanceledError") {
                console.log(err);
            }
        });
    }

    const handleExportExcel = async (keyword = "") => {
        try {
            if (controllerRef.current) {
                controllerRef.current.abort();
            }
            const controller = new AbortController();
            controllerRef.current = controller;

            await exportExcelService(keyword, controller.signal);

        } catch (err) {
            console.log("Export error:", err);
        }
    };

    const handleUpdate = (customerId) => {
        navigate(`/customer/update/${customerId}`);
    };

    const handleDelete = async (customerId) => {
        const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa khách hàng này không?");

        if (!isConfirm) return;

        try {
            await deleteCustomerService(customerId);
            fetchCustomers(page, size, keyword);
        } catch (err) {
            alert(err.message);
        }
    };

    useEffect(() => {
        fetchCustomers(page, size, keyword);
    }, [page, size, keyword]);

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách khách hàng" 
                    link="/partner/customers/add" 
                    titleButton="Thêm khách hàng"
                />

                <div className="card-body">
                    <TaskBar
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        onClick={() => handleExportExcel(keyword)}
                    />
                    <div className="table-responsive">
                        <FormTable names={names}>
                            {customers.map((customer) => (
                                <CustomerRow 
                                    key={customer.customerId} 
                                    customer={customer} 
                                    onUpdate={handleUpdate}
                                    onDelete={handleDelete}
                                />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination page={page} size={size} total={total} totalPages={totalPage} onPageChange={(newPage) => setPage(newPage)}/>
            </FormCardList>
        </HomeLayout>
    );
}