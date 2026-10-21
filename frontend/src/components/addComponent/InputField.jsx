export default function InputField({
  label,
  placeholder,
  required = false,
  readOnly = false,
  type = "text",
  col = "col-md-12",
  value,
  onChange
}) {
  return (
    <div className={col}>
      <div className="form-group">
        <label>{label}</label>

        <input
          type={type}
          className="form-control"
          placeholder={placeholder}
          required={required}
          readOnly={readOnly}
          value={value}
          onChange={onChange}
        />

        <div className="help-block with-errors"></div>
      </div>
    </div>
  );
}