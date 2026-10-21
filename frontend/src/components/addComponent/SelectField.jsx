import Select from "react-select";

export default function SelectField({ label, options, value, onChange, col = "col-md-12" }) {
  return (
    <div className={col}>
      <div className="form-group">
        <label>{label}</label>

        <Select
          options={options}
          value={options.find(o => o.value === value)}
          onChange={(selected) => onChange(selected.value)}
        />
      </div>
    </div>
  );
}