import ColumnName from "./ColumnName";


export default function FormTable({names, children}) {
    return (
        <table id="user-list-table" className="table table-striped dataTable mt-4" role="grid" aria-describedby="user-list-page-info"> 
            <ColumnName names={names}/>
            <tbody>
                {children}
            </tbody>
        </table>             
    );
}