import DefaultRow from "./DefaultRow";
import {formatDate} from "../../../../utils/date"

export default function ProductRow({product, imageUrl, onUpdate, onDelete}) {
    const activeText = product.active ? "Hoạt động" : "Ngừng";
    const activeClass = product.active ? "badge bg-success" : "badge bg-danger";
    const sellingPrice = product.price != null ? product.price.toLocaleString("vi-VN") : "0";
    const activeClass2 = product.deletedAt ? "badge bg-danger" : "";
    const activeAction = product.deletedAt ? false : true;

    return (
        <DefaultRow onUpdate={() => onUpdate(product.productId)} onDelete={() => onDelete(product.productId)} activeAction={activeAction}>
            <td>
                <div className="d-flex align-items-center">
                    <img src={imageUrl} className="img-fluid rounded avatar-50 mr-3" alt="image"/>
                    <div>
                        {product.name}
                        <p className="mb-0">
                            <small>{product.description}</small>
                        </p>
                    </div>
                </div>
            </td>

            <td>{product.sku}</td>
            <td>{product.categoryName}</td>
            <td>{product.unitName}</td>
            <td>{sellingPrice} $</td>
            <td> 
                <span className={activeClass}>
                    {activeText}
                </span> 
            </td>
            <td>{formatDate(product.createdAt)}</td>
            <td>{formatDate(product.updatedAt)}</td>
            <td>
                <span className = {activeClass2}>
                    {formatDate(product.deletedAt)}
                </span>
            </td>
        </DefaultRow>
    );
}