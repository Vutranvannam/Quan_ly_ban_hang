import Select from "react-select";

export default function SelectSearch({
  options = [],
  value,
  onChange,
  placeholder = "Chọn...",
  labelKey = "label",
  valueKey = "value"
}) {
  const formattedOptions = options.map(opt => ({
    label: opt[labelKey],
    value: opt[valueKey]
  }));

  const selectedValue =
    formattedOptions.find(opt => opt.value === value) || null;

  return (
    <Select
      options={formattedOptions}
      value={selectedValue}
      onChange={(opt) => onChange(opt ? opt.value : "")}
      placeholder={placeholder}
      isClearable={false}
      styles={{
        control: (base) => ({
          ...base,
          border: "none",
          boxShadow: "none",
          backgroundColor: "transparent"
        }),
        indicatorSeparator: () => ({
          display: "none"
        }),
        menuPortal: (base) => ({
          ...base,
          zIndex: 9999
        })
      }}
      menuPortalTarget={document.body}
    />
  );
}