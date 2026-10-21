import DefaultRow from "./DefaultRow";
import {formatDate} from "../../../../utils/date"

export default function UserRow({ user, onUpdate, onDelete }) {
    const roleColors = {
        ADMIN: "badge bg-warning-light",
        MANAGER: "badge bg-info-light",
        EMPLOYEE: "badge bg-success-light"
    };
    const activeText = user.enabled ? "Hoạt động" : "Ngừng";
    const activeClass = user.enabled ? "badge bg-success" : "badge bg-danger";
    const activeAction = user.deletedAt ? false : true;

    return (
        <DefaultRow onDelete={() => onDelete(user.userId)} onUpdate={() => onUpdate(user.userId)} activeAction={activeAction}>
            <td>{user.username}</td>
            <td>{user.email}</td>

            <td>{user.fullName || "-"}</td>

            <td>
                {user.roles && user.roles.length > 0 ? (
                    user.roles.map((role, index) => (
                        <span
                            key={index}
                            className={`${roleColors[role] || "badge bg-danger-light"} d-block mb-1`}
                        >
                            {role}
                        </span>
                    ))
                ) : (
                    "-"
                )}
            </td>

            <td>
                <span className = {activeClass}>
                    {activeText}
                </span>
            </td>

            <td>{user.locked ?  "Bị khóa" : "Không khóa"}</td>

            <td>{formatDate(user.createdAt)}</td>
            <td>{formatDate(user.updatedAt)}</td>
            <td>{formatDate(user.deletedAt)}</td>
        </DefaultRow>
    );
}