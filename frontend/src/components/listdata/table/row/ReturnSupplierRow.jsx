import DefaultRow from "./DefaultRow";

export default function ReturnSupplierRow({ returnOrder }) {
    return (
        <DefaultRow>
            <td>{returnOrder.id}</td>
            <td>{returnOrder.supplier_name}</td>
            <td>{returnOrder.created_by_name}</td>
            <td>{returnOrder.status}</td>
            <td>{returnOrder.created_at}</td>
            <td>{returnOrder.note || "-"}</td>
        </DefaultRow>
    );
}