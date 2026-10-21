export default function ColumnName({names}) {
    return(
        <thead>
            <tr className="ligth">
                {names.map((col) => (
                    <th key={col.key}>{col.title}</th>
                ))}
                <th>Xóa</th>
            </tr>
        </thead>
    );
    
}