export default function TextAreaField({ label, value, onChange }) {
  return (
    <div className="col-md-12">
      <div className="form-group">
        <label>{label}</label>

        <textarea className="form-control" rows="4" value={value} onChange={onChange}></textarea>
        
      </div>
    </div>
  );
}