import DefaultRow from "./DefaultRow";
import {formatDate} from "../../../../utils/date"

export default function CategoryRow({category, onDelete, onUpdate}) {

    const activeAction = category.deletedAt ? false : true;

    return (
        <DefaultRow onDelete={() => onDelete(category.categoryId)} onUpdate={() => onUpdate(category.categoryId)} activeAction={activeAction}>
            <td>{category.name}</td>
            <td>{category.description}</td>
            <td>{formatDate(category.createdAt)}</td>
            <td>{formatDate(category.updatedAt)}</td>
            <td>{formatDate(category.deletedAt)}</td>
        </DefaultRow>
    );
}