export default function BestItem({ name, sell, earned, img, color }) {
  return (
    <div className="card card-block card-stretch card-height-helf">
      <div className="card-body card-item-right">
        <div className="d-flex align-items-top">

          <div className={`bg-${color}-light rounded`}>
            <img
              src={img}
              className="style-img img-fluid m-auto"
              alt="image"
            />
          </div>

          <div className="style-text text-left">
            <h5 className="mb-2">{name}</h5>
            <p className="mb-2">Total Sell : {sell}</p>
            <p className="mb-0">Total Earned : {earned}</p>
          </div>

        </div>
      </div>
    </div>
  );
}