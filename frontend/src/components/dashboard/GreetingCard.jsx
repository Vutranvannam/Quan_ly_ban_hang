export default function GreetingCard({ name = "Phúc", time = "Buổi sáng tốt lành" }) {
  return (
    <div className="col-lg-4">
      <div className="card card-transparent card-block card-stretch card-height border-none">
        <div className="card-body p-0 mt-lg-2 mt-0">
          <h3 className="mb-3">
            Chào {name}, {time}
          </h3>

          <p className="mb-0 mr-4">
            Bảng điều khiển của bạn cung cấp cho bạn chế độ xem về hiệu suất chính hoặc quy trình kinh doanh.
          </p>
        </div>
      </div>
    </div>
  );
}