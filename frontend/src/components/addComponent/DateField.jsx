export default function DateField({
  label,
  col = "col-md-12",
  required = false
}) {
  return (
    <div className={col}>
      <div className="form-group">

        <label>{label}</label>

        <input
          type="date"
          className="form-control"
          required={required}
        />

        <div className="help-block with-errors"></div>

      </div>
    </div>
  );
}