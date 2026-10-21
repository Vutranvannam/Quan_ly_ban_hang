

function AuthLayout({children }) {
  return (
    <div className="wrapper">
      <section className="login-content">
        <div className="container">
          <div className="row align-items-center justify-content-center height-self-center">
            <div className="col-lg-8">
              <div className="card auth-card">
                <div className="card-body p-0">
                  <div className="d-flex align-items-center auth-content">

                    <div className="col-lg-7 align-self-center">
                      <div className="p-3">
                        {children}
                      </div>
                    </div>

                    <div className="col-lg-5 content-right">
                      <img
                        src="/assets/images/login/01.png"
                        className="img-fluid image-right"
                        alt=""
                      />
                    </div>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default AuthLayout;