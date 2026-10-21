export default function TaskBar({active = true, value, onChange, onClick}) {
    return (
        <div className="row justify-content-between">
            <div className="col-sm-6 col-md-6">
                <div id="user_list_datatable_info" className="dataTables_filter">
                    <form className="mr-3 position-relative" onSubmit={(e) => e.preventDefault()}>
                        <div className="form-group mb-0">
                            <input 
                                type="search" 
                                className="form-control" 
                                id="exampleInputSearch" 
                                placeholder="Search" 
                                aria-controls="user-list-table"
                                value={value}
                                onChange={onChange}
                            />
                        </div>
                    </form>
                </div>
            </div>
            {
                active && ( 
                    <div className="col-sm-6 col-md-6">
                        <div className="user-list-files d-flex">
                            <a
                                className="bg-primary"
                                href="#"
                                onClick={(e) => {
                                    e.preventDefault();
                                    onClick();
                                }}
                            >
                                Excel
                            </a>
                        </div>
                    </div>
                )
            }
           
        </div>        
    );
}