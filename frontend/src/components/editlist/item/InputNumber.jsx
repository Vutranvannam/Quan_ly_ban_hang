export default function InputNumber({
  value,
  onChange,
  min = 0,
  width = "80px"
}) {
  return (
    <input type="number" value={value} min={min}
      onChange={(e) => onChange(Number(e.target.value))}
      style={{
        width: width,
        border: "none",
        outline: "none",
        boxShadow: "none",
        background: "transparent"
      }}
    />
  );
}