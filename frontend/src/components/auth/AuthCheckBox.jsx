function AuthCheckBox({ id, label, checked, onChange, col = "col-lg-6", labelClass = "custom-control-label" }) {
  return (
    <div className={col}>
      <div className="custom-control custom-checkbox mb-3">
        <input type="checkbox" className="custom-control-input" id={id} checked={checked} onChange={onChange}/>
        <label className={labelClass} htmlFor={id}> {label} </label>
      </div>
    </div>
  );
}

export default AuthCheckBox;