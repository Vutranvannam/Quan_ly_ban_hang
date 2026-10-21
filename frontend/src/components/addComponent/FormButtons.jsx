export default function FormButtons({ onClick, submitText, addReset }) {
  return (
    <>
      <button type="submit" className="btn btn-primary mr-2" onClick={onClick}>
        {submitText}
      </button>

      <button type="reset" className="btn btn-danger" onClick={addReset}>
        Reset
      </button>
    </>
  );
}