export default function TopProductItem({ name, count, img, color }) {
  return (
    <div style={{ padding: "0 10px" }}>
      <div className="card card-block card-stretch card-height mb-0">

        <div className="card-body">

          <div className={`bg-${color}-light rounded`}>
            <img src={img} className="style-img img-fluid m-auto p-3" alt="image"/>
          </div>

          <div className="style-text text-left mt-3">
            <h5 className="mb-1">{name}</h5>
            <p className="mb-0">{count} Item</p>
          </div>

        </div>

      </div>
    </div>
  );
}