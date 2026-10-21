import Slider from "react-slick";
import BestItem from "./BestItem";

export default function BestItems() {

  const items = [
    { name: "Coffee Beans Packet",  sell: "45897",  earned: "$45,89 M",  img: "/assets/images/product/04.png", color: "warning" },
    { name: "Bottle Cup Set", sell: "44359", earned: "$45,50 M", img: "/assets/images/product/05.png", color: "danger" },
    { name: "Nguyễn Minh Phúc", sell: "45897", earned: "$45,89 M", img: "/assets/images/product/04.png", color: "info" }
  ];

  const settings = {
    infinite: true,
    slidesToShow: 2,
    slidesToScroll: 1,
    vertical: true,        // scroll dọc
    verticalSwiping: true,
    autoplay: true,
    autoplaySpeed: 2500,
    arrows: false
  };

  return (
    <div className="col-lg-4">

      <div className="card card-transparent card-block card-stretch mb-4">
        <div className="card-header d-flex align-items-center justify-content-between p-0">

          <div className="header-title">
            <h4 className="card-title mb-0">Best Item All Time</h4>
          </div>

          <div className="card-header-toolbar d-flex align-items-center">
            <a href="#" className="btn btn-primary view-btn font-size-14">
              View All
            </a>
          </div>

        </div>
      </div>

      <Slider {...settings}>
        {items.map((item, index) => (
          <BestItem
            key={index}
            name={item.name}
            sell={item.sell}
            earned={item.earned}
            img={item.img}
            color={item.color}
          />
        ))}
      </Slider>

    </div>
  );
}