export default function FormCard({ title, children }) {
  return (
    <div className="content-page">
      <div className="container-fluid add-form-list">
        <div className="row">
          <div className="col-sm-12">
            <div className="card">

              <div className="card-header d-flex justify-content-between">
                <div className="header-title">
                  <h4 className="card-title">{title}</h4>
                </div>
              </div>

              <div className="card-body">
                {children}
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  );
}