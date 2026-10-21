export default function StatsItem({ title, value, img, color, progress }) {
  return (
    <div className="col-lg-4 col-md-4">
      <div className="card card-block card-stretch card-height">
        <div className="card-body">

          <div className="d-flex align-items-center mb-4 card-total-sale">
            <div className={`icon iq-icon-box-2 bg-${color}-light`}>
              <img src={img} className="img-fluid" alt="image" />
            </div>

            <div>
              <p className="mb-2">{title}</p>
              <h4>{value}</h4>
            </div>
          </div>

          <div className="iq-progress-bar mt-2">
            <span
              className={`bg-${color} iq-progress progress-1`}
              style={{ width: `${progress}%` }}
            />
          </div>

        </div>
      </div>
    </div>
  );
}