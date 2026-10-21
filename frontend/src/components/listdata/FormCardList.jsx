export default function FormCardList({children}) {
    return (
        <div className="content-page">
            <div className="container-fluid">
                <div className="row">
                    <div className="col-sm-12">
                        <div className="card">
                            {children}
                        </div>
                    </div>
                </div>
            </div>
        </div>                
    )
}