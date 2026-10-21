import usePagination from "../../hooks/ui/usePagination";
import useKeyword from "../../hooks/ui/useKeyword";
import useUserList from "../../hooks/api/user/useUserList";
import useDeleteUser from "../../hooks/api/user/useDeleteUser";
import useUserNavigate from "../../hooks/router/useUserNavigate";


import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import UserRow from "../../components/listdata/table/row/UserRow";

export default function ListUserPage() {

    
    const names = ["Tên đăng nhập", "Email", "Họ tên", "Vai trò", "Trạng thái", "Khóa", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"];

    const {
        page, size, total, totalPage,
        setPage, setSize, setTotal, setTotalPage,
    } = usePagination();

    const {
        keyword,
        setKeyword,
    } = useKeyword();

    const {
        users,
        fetchUsers,
    } = useUserList(
        page, 
        size, 
        keyword, 
        (res) => {
            setTotal(res.total);
            setTotalPage(res.totalPages);
        }
    );

    const { handleDelete } = useDeleteUser(fetchUsers);

    const { goToAddUser, goToUpdateUser } = useUserNavigate();

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách người dùng" 
                    onClick={goToAddUser}
                    titleButton="Thêm người dùng"
                />

                <div className="card-body">
                    <TaskBar 
                        active={false}
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                    />
                    <div className="table-responsive">
                        
                        <FormTable names={names}>
                            {users.map((user) => (
                                <UserRow 
                                    key={user.userId} 
                                    user={user} 
                                    onDelete={handleDelete}
                                    onUpdate={goToUpdateUser}
                                />
                            ))}
                        </FormTable>
                    </div>
                </div>

                <Pagination page={page} size={size} total={total} totalPages={totalPage} onPageChange={(newPage) => setPage(newPage)} />
            </FormCardList>
        </HomeLayout>
    );
}