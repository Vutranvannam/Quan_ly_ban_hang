import StatsItem from "./StatsItem";

export default function StatsSection() {
  return (
    <div className="col-lg-8">
      <div className="row">

        <StatsItem title="Total Sales" value="31.50" img="/assets/images/product/1.png" color="info" progress={85}/>
        <StatsItem title="Total Cost" value="$4598" img="/assets/images/product/2.png" color="danger" progress={70}/>
        <StatsItem title="Product Sold" value="4589 M" img="/assets/images/product/3.png" color="success" progress={75}/>

      </div>
    </div>
  );
}