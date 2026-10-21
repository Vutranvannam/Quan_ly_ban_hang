
import usePagination from "../../hooks/ui/usePagination";
import useKeyword from "../../hooks/ui/useKeyword";
import useCategoryList from "../../hooks/api/category/useCategoryList";
import useDeleteCategory from "../../hooks/api/category/useDeleteCategory";
import useExportCategoryExcel from "../../hooks/api/category/useExportCategoryExcel";
import useCategoryNavigate from "../../hooks/router/useCategoryNavigate";

import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";  
import CategoryRow from "../../components/listdata/table/row/CategoryRow";

export default function ListCategoryPage() {
    const names = ["Tên", "Mô tả", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"];

    const {
        page, size, total, totalPage,
        setPage, setSize, setTotal, setTotalPage,
    } = usePagination();

    const {
        keyword,
        setKeyword,
    } = useKeyword();

    const {
        categories,
        fetchCategories,
    } = useCategoryList(
        page, 
        size, 
        keyword, 
        (res) => {
            setTotal(res.total);
            setTotalPage(res.totalPages);
        }
    );

    const { handleDelete } = useDeleteCategory(fetchCategories);

    const { goToAddCategory, goToUpdateCategory } = useCategoryNavigate();

    const { exportCategories } = useExportCategoryExcel();

    return (
        <HomeLayout>
            <FormCardList>
                <Header 
                    title="Danh sách loại linh kiện" 
                    onClick={goToAddCategory} 
                    titleButton="Thêm loại linh kiện"
                />

                <div className="card-body">
                    <TaskBar
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        onClick={() => exportCategories(keyword)}
                    />
                    <div className="table-responsive">
                        
                        <FormTable names={names}>
                            {categories.map((category) => (
                                <CategoryRow 
                                    key={category.categoryId} 
                                    category={category} 
                                    onDelete={handleDelete}
                                    onUpdate={goToUpdateCategory}
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