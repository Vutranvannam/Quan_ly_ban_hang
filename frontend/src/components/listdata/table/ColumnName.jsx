export default function ColumnName({names}) {
    return(
        <thead>
            <tr className="ligth">
                {names.map((name, index) => (
                    <th key={index}>{name}</th>
                ))}
                <th style={{ marginRight: "10px" }}>Action</th>
            </tr>
        </thead>
    );
    
}