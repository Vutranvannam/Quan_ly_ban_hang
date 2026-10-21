import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import ReturnCustomerRow from "../../components/listdata/table/row/ReturnCustomerRow";

export default function ListReturnCustomerPage() {
    const names = ["Mã phiếu", "Đơn bán", "Người tạo", "Trạng thái", "Ngày tạo", "Ghi chú"];

    // Mock dữ liệu customer_returns
    const returnOrders = [
        {
            id: 1,
            sales_order_code: "SO-001",
            created_by_name: "Nguyễn Văn A",
            status: "DRAFT",
            created_at: "2026-03-01 09:00",
            note: "Đang tạo phiếu"
        },
        {
            id: 2,
            sales_order_code: "SO-002",
            created_by_name: "Trần Thị B",
            status: "PENDING",
            created_at: "2026-03-02 10:30",
            note: ""
        },
        {
            id: 3,
            sales_order_code: "SO-003",
            created_by_name: "Lê Văn C",
            status: "APPROVED",
            created_at: "2026-03-03 14:15",
            note: "Đã duyệt"
        },
        {
            id: 4,
            sales_order_code: "SO-004",
            created_by_name: "Nguyễn Văn A",
            status: "COMPLETED",
            created_at: "2026-03-04 08:45",
            note: "Đã nhập lại kho"
        },
        {
            id: 5,
            sales_order_code: "SO-005",
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
                    title="Danh sách phiếu trả hàng khách hàng" 
                    link="/return/customer/add" 
                    titleButton="Thêm phiếu trả"
                />

                <div className="card-body">
                    <div className="table-responsive">
                        <TaskBar />
                        <FormTable names={names}>
                            {returnOrders.map((order, index) => (
                                <ReturnCustomerRow key={index} returnOrder={order} />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination />
            </FormCardList>
        </HomeLayout>
    );
}