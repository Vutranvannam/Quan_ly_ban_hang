import DefaultRow from "./DefaultRow";

export default function ReturnCustomerRow({ returnOrder }) {
    return (
        <DefaultRow>
            <td>{returnOrder.id}</td>
            <td>{returnOrder.sales_order_code}</td>
            <td>{returnOrder.created_by_name}</td>
            <td>{returnOrder.status}</td>
            <td>{returnOrder.created_at}</td>
            <td>{returnOrder.note || "-"}</td>
        </DefaultRow>
    );
}