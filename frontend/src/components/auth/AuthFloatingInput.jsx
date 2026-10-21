function FloatingInput({id, type, label, value, onChange}) {
  return (
    <div className="col-lg-12">
      <div className={`floating-label form-group ${value ? 'has-value' : ''}`}>
        <input
          id={id}
          className="floating-input form-control"
          type={type}
          placeholder=" "
          value={value}
          onChange={onChange}
        />
        <label>{label}</label>
      </div>
    </div>
  );
}

export default FloatingInput;