import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import ReturnSupplierRow from "../../components/listdata/table/row/ReturnSupplierRow";

export default function ListReturnSupplierPage() {
    const names = ["Mã phiếu", "Nhà cung cấp", "Người tạo", "Trạng thái", "Ngày tạo", "Ghi chú"];

    // Mock dữ liệu supplier_returns
    const returnOrders = [
        {
            id: 1,
            supplier_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "DRAFT",
            created_at: "2026-03-01 09:00",
            note: "Đang tạo phiếu"
        },
        {
            id: 2,
            supplier_name: "Công ty XYZ",
            created_by_name: "Trần Thị B",
            status: "PENDING",
            created_at: "2026-03-02 10:30",
            note: ""
        },
        {
            id: 3,
            supplier_name: "Công ty 123",
            created_by_name: "Lê Văn C",
            status: "APPROVED",
            created_at: "2026-03-03 14:15",
            note: "Đã duyệt"
        },
        {
            id: 4,
            supplier_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "COMPLETED",
            created_at: "2026-03-04 08:45",
            note: "Đã trả hàng"
        },
        {
            id: 5,
            supplier_name: "Công ty XYZ",
            created_by_name: "Trần Thị B",
            status: "CANCELLED",
            created_at: "2026-03-05 11:00",
            note: "Hủy phiếu"
        }
    ];

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách phiếu trả hàng nhà cung cấp" 
                    link="/return/supplier/add" 
                    titleButton="Thêm phiếu trả"
                />

                <div className="card-body">
                    <div className="table-responsive">
                        <TaskBar />
                        <FormTable names={names}>
                            {returnOrders.map((order, index) => (
                                <ReturnSupplierRow key={index} returnOrder={order} />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination />
            </FormCardList>
        </HomeLayout>
    );
}