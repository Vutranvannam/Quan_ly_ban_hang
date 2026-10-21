import Header from "./Header";
import ColumnName from "./ColumnName";
export default function EditableTable({title, names, children, addRow }) {
    return (
            <div className="col-sm-13">
                <div className="card">

                    <Header title={title} />

                    <div className="card-body">

                        <span className="table-add float-right mb-3 mr-2">
                            <button className="btn btn-sm bg-primary" type="button" onClick={addRow}>Thêm mới</button>
                        </span>
                        <table className="table table-bordered table-responsive-md table-striped text-center">
                            <ColumnName names = {names}/>
                            <tbody>
                                {children}
                            </tbody>
                        </table>
                        
                    </div>
                </div>
            </div>
    );
}