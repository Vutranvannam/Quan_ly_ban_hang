import DefaultRow from "./DefaultRow";

export default function ExportRow({ order }) {
    return (
        <DefaultRow>
            <td>{order.id}</td>
            <td>{order.customer_name}</td>
            <td>{order.created_by_name}</td>
            <td>{order.status}</td>
            <td>{order.manufacture_date || "-"}</td>
            <td>{order.expiry_date || "-"}</td>
            <td>{order.created_at}</td>
            <td>{order.note || "-"}</td>
        </DefaultRow>
    );
}