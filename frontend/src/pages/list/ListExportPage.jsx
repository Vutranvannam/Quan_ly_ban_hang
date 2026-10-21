import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import ExportRow from "../../components/listdata/table/row/ExportRow";

export default function ListExportPage() {
    const names = ["Mã đơn", "Khách hàng", "Người tạo", "Trạng thái", "Ngày sản xuất", "Hạn sử dụng", "Ngày tạo", "Ghi chú"];

    // Mock dữ liệu sales_orders
    const salesOrders = [
        {
            id: 1,
            customer_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "DRAFT",
            manufacture_date: "2026-03-01",
            expiry_date: "2026-12-31",
            created_at: "2026-03-01 09:00",
            note: "Đơn đang tạo"
        },
        {
            id: 2,
            customer_name: "Công ty XYZ",
            created_by_name: "Trần Thị B",
            status: "PENDING",
            manufacture_date: "2026-03-02",
            expiry_date: "2026-12-30",
            created_at: "2026-03-02 10:30",
            note: ""
        },
        {
            id: 3,
            customer_name: "Công ty 123",
            created_by_name: "Lê Văn C",
            status: "APPROVED",
            manufacture_date: "2026-03-03",
            expiry_date: "2026-12-29",
            created_at: "2026-03-03 14:15",
            note: "Đã duyệt"
        },
        {
            id: 4,
            customer_name: "Công ty ABC",
            created_by_name: "Nguyễn Văn A",
            status: "COMPLETED",
            manufacture_date: "2026-03-04",
            expiry_date: "2026-12-28",
            created_at: "2026-03-04 08:45",
            note: "Đã xuất kho"
        },
        {
            id: 5,
            customer_name: "Công ty XYZ",
            created_by_name: "Trần Thị B",
            status: "CANCELLED",
            manufacture_date: "2026-03-05",
            expiry_date: "2026-12-27",
            created_at: "2026-03-05 11:00",
            note: "Hủy đơn"
        }
    ];

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách đơn xuất" 
                    link="/export/add" 
                    titleButton="Thêm đơn xuất"
                />

                <div className="card-body">
                    <div className="table-responsive">
                        <TaskBar />
                        <FormTable names={names}>
                            {salesOrders.map((order, index) => (
                                <ExportRow key={index} order={order} />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination />
            </FormCardList>
        </HomeLayout>
    );
}