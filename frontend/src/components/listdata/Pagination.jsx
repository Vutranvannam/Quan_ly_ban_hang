export default function Pagination({page, size, total, totalPages, onPageChange}) {
    
    const start = page*size + 1;
    const end = Math.min((page + 1) * size, total);
    const pages = Array.from({ length: totalPages }, (_, i) => i);

    return (
        <div className="row justify-content-between mt-3">
            <div id="user-list-page-info" className="col-md-6">
                <span>Hiển thị [{start} - {end}] trong {total} kết quả</span>
            </div>
            <div className="col-md-6">
                <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-end mb-0">

                    <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                        <a 
                            className="page-link" 
                            href="#" tabIndex="-1"
                            onClick={() => onPageChange(page - 1)}
                            disabled={page === 0}
                        >
                            Trước
                        </a>
                    </li>

                    {pages.map((p) => (
                        <li 
                            key={p}
                            className={`page-item ${p === page ? "active" : ""}`}
                        >
                            <button 
                                className="page-link"
                                onClick={() => onPageChange(p)}
                            >
                                {p + 1}
                            </button>
                        </li>
                    ))}

                    <li className={`page-item ${page === totalPages - 1 ? "disabled" : ""}`}>
                         <a 
                            className="page-link" 
                            href="#" tabIndex="-1"
                            onClick={() => onPageChange(page + 1)}
                            disabled={page === totalPages - 1}
                        >
                            Tiếp theo
                        </a>
                    </li>

                </ul>
                </nav>
            </div>
        </div>
    );
}   