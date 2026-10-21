import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { getSupplierListService, exportExcelService, deleteSupplierService } from "../../services/supplierService";

import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import SupplierRow from "../../components/listdata/table/row/SupplierRow";

export default function ListSupplierPage() {
    const names = ["Tên nhà cung cấp", "SĐT", "Email", "Địa chỉ", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"];

    const [suppliers, setSuppliers] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [total, setTotal] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [keyword, setKeyword] = useState("");

    const controllerRef = useRef(null);
    const navigate = useNavigate();

    const fetchSuppliers = async (page, size, keyword = "") => {
        if (controllerRef.current) {
            controllerRef.current.abort();
        }
        const controller = new AbortController();
        controllerRef.current = controller;

        getSupplierListService(page, size, keyword, controller.signal).then(res => {
            setSuppliers(res.content);
            setPage(res.page);
            setSize(res.size);
            setTotal(res.total);
            setTotalPage(res.totalPages);
        })
        .catch((err) => {
            if (err.name !== "CanceledError") {
                console.log(err);
            }
        })
    };

    const handleUpdate = (supplierId) => {
        navigate(`/supplier/update/${supplierId}`);
    };

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

    const handleDelete = async (supplierId) => {
        const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa nhà cung cấp này không?");

        if (!isConfirm) return;

        try {
            await deleteSupplierService(supplierId);
            fetchSuppliers(page, size, keyword);
        } catch (err) {
            alert(err.message);
        }
    };

    useEffect(() => {
        fetchSuppliers(page, size, keyword);
    }, [page, size, keyword]);

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách nhà cung cấp" 
                    link="/partner/suppliers/add" 
                    titleButton="Thêm nhà cung cấp"
                />

                <div className="card-body">
                    <TaskBar
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        onClick={() => handleExportExcel(keyword)}
                    />
                    <div className="table-responsive">
                        <FormTable names={names}>
                            {suppliers.map((supplier) => (
                                <SupplierRow 
                                    key={supplier.supplierId} 
                                    supplier={supplier} 
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