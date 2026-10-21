import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import AdjustmentRow from "../../components/listdata/table/row/AdjustmentRow";

export default function ListAdjustmentPage() {
    const names = ["Mã phiếu", "Người tạo", "Ngày tạo", "Ghi chú"];

    // Mock dữ liệu inventory_adjustments
    const adjustments = [
        {
            id: 1,
            created_by_name: "Nguyễn Văn A",
            created_at: "2026-03-01 09:00",
            note: "Điều chỉnh định kỳ"
        },
        {
            id: 2,
            created_by_name: "Trần Thị B",
            created_at: "2026-03-02 10:30",
            note: ""
        },
        {
            id: 3,
            created_by_name: "Lê Văn C",
            created_at: "2026-03-03 14:15",
            note: "Kiểm tra kho sau nhập hàng"
        },
        {
            id: 4,
            created_by_name: "Nguyễn Văn A",
            created_at: "2026-03-04 08:45",
            note: ""
        },
        {
            id: 5,
            created_by_name: "Trần Thị B",
            created_at: "2026-03-05 11:00",
            note: "Chênh lệch tồn kho"
        }
    ];

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách phiếu điều chỉnh tồn kho" 
                    link="/adjustment/add" 
                    titleButton="Thêm phiếu điều chỉnh"
                />

                <div className="card-body">
                    <div className="table-responsive">
                        <TaskBar />
                        <FormTable names={names}>
                            {adjustments.map((adjustment, index) => (
                                <AdjustmentRow key={index} adjustment={adjustment} />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination />
            </FormCardList>
        </HomeLayout>
    );
}