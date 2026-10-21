export default function InputDate({ value, onChange }) {
  return (
    <input
      type="date"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      style={{
        width: "100%",
        border: "none",
        outline: "none",
        boxShadow: "none",
        background: "transparent"
      }}
    />
  );
}