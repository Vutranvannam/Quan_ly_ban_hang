import DefaultRow from "./DefaultRow";

export default function AdjustmentRow({ adjustment }) {
    return (
        <DefaultRow>
            <td>{adjustment.id}</td>
            <td>{adjustment.created_by_name}</td>
            <td>{adjustment.created_at}</td>
            <td>{adjustment.note || "-"}</td>
        </DefaultRow>
    );
}