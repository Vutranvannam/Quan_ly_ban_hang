import DefaultRow from "./DefaultRow";

export default function ImportRow({ order }) {
    return (
        <DefaultRow>
            <td>{order.id}</td>
            <td>{order.supplier_name}</td>
            <td>{order.created_by_name}</td>
            <td>{order.status}</td>
            <td>{order.created_at}</td>
            <td>{order.note || "-"}</td>
        </DefaultRow>
    );
}