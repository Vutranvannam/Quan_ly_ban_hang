function AuthButton({ text, type = "submit", onClick }) {
  return (
    <button type={type} className="btn btn-primary" onClick={onClick}>
      {text}
    </button>
  );
}

export default AuthButton;