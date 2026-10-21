import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { getProductListService, exportExcelService, deleteProductService } from "../../services/productService";

import HomeLayout from "../../layouts/HomeLayout";
import Header from "../../components/listdata/Header";
import FormCardList from "../../components/listdata/FormCardList";
import FormTable from "../../components/listdata/table/FormTable";
import TaskBar from "../../components/listdata/table/TaskBar";
import Pagination from "../../components/listdata/Pagination";
import ProductRow from "../../components/listdata/table/row/ProductRow";


export default function ProductPage() {
    const names = ["Sản phẩm", "SKU", "Loại linh kiện", "Đơn vị", "Giá bán", "Trạng thái", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"];

    const [products, setProducts] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [total, setTotal] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [keyword, setKeyword] = useState("");

    const controllerRef = useRef(null);
    const navigate = useNavigate();

    const fetchProducts = async (page, size, keyword = "") => {

        if (controllerRef.current) {
            controllerRef.current.abort();
        }
        const controller = new AbortController();
        controllerRef.current = controller;
        
        getProductListService(page, size, keyword, controller.signal).then(res => {

            const productsWithImage = res.content.map(product => {
                const primary = product.productImages?.find(img => img.isPrimary);

                return {
                    ...product,
                    imageUrl: primary?.imageUrl || null,
                    imageId: primary?.altText || null
                };
            });

            setProducts(productsWithImage);
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

    const handleUpdate = async (productId) => {
        navigate(`/product/update/${productId}`);
    }

    const handleDelete = async (productId) => {
        const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?");

        if (!isConfirm) return;

        try {
            await deleteProductService(productId);
            fetchProducts(page, size, keyword);
        } catch (err) {
            alert(err.message);
        }
    };

    useEffect(() => {
        fetchProducts(page, size, keyword);
    }, [page, size, keyword]);

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

    return (
        <HomeLayout>
            <FormCardList>
                <Header title="Danh sách sản phẩm" link="/product/add" titleButton="Thêm sản phẩm"/>
                    <div className="card-body">
                        <TaskBar
                            value={keyword}
                            onChange={(e) => setKeyword(e.target.value)}
                            onClick={() => handleExportExcel(keyword)}
                        />
                        <div className="table-responsive">

                            <FormTable names={names}>
                                {products.map((product) => (
                                    <ProductRow 
                                        key={product.productId} 
                                        product={product} 
                                        imageUrl={product.imageUrl} 
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