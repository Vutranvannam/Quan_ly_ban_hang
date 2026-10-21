import Slider from "react-slick";
import TopProductItem from "./TopProductItem";
import TimeFilterDropdown from "../TimeFilterDropdown";

export default function TopProducts() {

  const products = [
    { name: "Organic Cream", count: 789, img: "/assets/images/product/01.png", color: "warning" },
    { name: "Rain Umbrella", count: 657, img: "/assets/images/product/02.png", color: "danger" },
    { name: "Serum Bottle", count: 489, img: "/assets/images/product/03.png", color: "info" },
    { name: "Organic Cream", count: 468, img: "/assets/images/product/02.png", color: "success" }
  ];

  const settings = {
    dots: false,
    infinite: true,
    slidesToShow: 3,      // hiển thị 3 sản phẩm
    slidesToScroll: 1,
    autoplay: true,       // tự chạy
    autoplaySpeed: 2000,  // 2s trượt 1 lần
    arrows: false
  };

  return (
    <div className="col-lg-8">
      <div className="card card-block card-stretch card-height">

        <div className="card-header d-flex align-items-center justify-content-between">
          <div className="header-title">
              <h4 className="card-title">Top Products</h4>
          </div>
          <TimeFilterDropdown/>
        </div>
        <div className="card-body">

          <Slider {...settings}>
            {products.map((p, index) => (
              <TopProductItem key={index} name={p.name} count={p.count} img={p.img} color={p.color}/>
            ))}
          </Slider>

        </div>
      </div>
    </div>
  );
}