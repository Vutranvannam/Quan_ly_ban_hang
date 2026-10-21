import CreatableSelect from "react-select/creatable";

export default function CreatableSelectField({
  label,
  options = [],
  value,
  onChange,
  col = "col-md-12"
}) {
  return (
    <div className={col}>
      <label>{label}</label>

      <CreatableSelect
    options={options}
    value={options.find(opt => opt.label === value) || null}
    onChange={(selected) => {
      onChange(selected ? selected.label : null);
    }}
    onCreateOption={(inputValue) => {
      onChange(inputValue);
    }}
    isClearable
  />
    </div>
  );
}