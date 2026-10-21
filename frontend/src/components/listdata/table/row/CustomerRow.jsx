import DefaultRow from "./DefaultRow";
import {formatDate} from "../../../../utils/date"

export default function CustomerRow({ customer, onUpdate, onDelete }) {

    const activeClass = customer.deletedAt ? "badge bg-danger" : "";
    const activeAction = customer.deletedAt ? false : true;

    return (
        <DefaultRow onUpdate={() => onUpdate(customer.customerId)} onDelete={() => onDelete(customer.customerId)} activeAction={activeAction}>
            <td>{customer.name}</td>
            <td>{customer.phone}</td>
            <td>{customer.email}</td>
            <td>{customer.address}</td>
            <td>{formatDate(customer.createdAt)}</td>
            <td>{formatDate(customer.updatedAt)}</td>
            <td>
                <span className = {activeClass}>
                    {formatDate(customer.deletedAt)}
                </span>
            </td>
        </DefaultRow>
    );
}