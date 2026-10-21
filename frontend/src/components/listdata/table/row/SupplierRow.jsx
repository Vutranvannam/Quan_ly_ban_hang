import DefaultRow from "./DefaultRow";
import {formatDate} from "../../../../utils/date"

export default function SupplierRow({ supplier, onUpdate, onDelete }) {

    const activeClass = supplier.deletedAt ? "badge bg-danger" : "";
    const activeAction = supplier.deletedAt ? false : true;

    return (
        <DefaultRow onUpdate={() => onUpdate(supplier.supplierId)} onDelete={() => onDelete(supplier.supplierId)} activeAction={activeAction}>
            <td>{supplier.name}</td>
            <td>{supplier.phone}</td>
            <td>{supplier.email}</td>
            <td>{supplier.address}</td>
            <td>{formatDate(supplier.createdAt)}</td>
            <td>{formatDate(supplier.updatedAt)}</td>
            <td>
                <span className = {activeClass}>
                    {formatDate(supplier.deletedAt)}
                </span>
            </td>
        </DefaultRow>
    );
}