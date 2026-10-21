import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import ImportRow from "../../components/listdata/table/row/ImportRow";

export default function ListImportPage() {
    const names = ["Mã đơn", "Nhà cung cấp", "Người tạo", "Trạng thái", "Ngày tạo", "Ghi chú"];

    // Mock dữ liệu import_orders
    const importOrders = [
        {
            id: 1,
            supplier_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "DRAFT",
            created_at: "2026-03-01 09:00",
            note: "Đơn nhập lần đầu"
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
            note: "Đơn cần duyệt nhanh"
        },
        {
            id: 4,
            supplier_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "COMPLETED",
            created_at: "2026-03-04 08:45",
            note: "Nhập kho thành công"
        },
        {
            id: 5,
            supplier_name: "Công ty XYZ",
            created_by_name: "Trần Thị B",
            status: "CANCELLED",
            created_at: "2026-03-05 11:00",
            note: "Hủy do lỗi đơn"
        }
    ];

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách đơn nhập" 
                    link="/import/add" 
                    titleButton="Thêm đơn nhập"
                />

                <div className="card-body">
                    <div className="table-responsive">
                        <TaskBar />
                        <FormTable names={names}>
                            {importOrders.map((order, index) => (
                                <ImportRow key={index} order={order} />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination />
            </FormCardList>
        </HomeLayout>
    );
}