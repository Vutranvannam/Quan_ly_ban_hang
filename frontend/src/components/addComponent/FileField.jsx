export default function FileField({ label, onChange }) {
  return (
    <div className="col-md-12">
      <div className="form-group">
        <label>{label}</label>

        <input
          type="file"
          className="form-control image-file"
          accept="image/*"
          onChange={(e) => onChange(e.target.files[0])}
        />

      </div>
    </div>
  );
}