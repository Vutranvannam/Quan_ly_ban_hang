import Select from "react-select";

export default function MultiSelectField({label, options, value, onChange, col = "col-md-12"}) {
  return (
    <div className={col}>
      <div className="form-group">
        <label>{label}</label>

        <Select
          options={options}
          isMulti={true}
          value={
            options.filter(o => (value || []).includes(o.value))
          }
          onChange={(selected) => {
            onChange(selected ? selected.map(s => s.value) : []);
          }}
        />
      </div>
    </div>
  );
}