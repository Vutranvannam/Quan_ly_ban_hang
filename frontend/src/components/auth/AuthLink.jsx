function AuthLink({ href, text }) {
  return (
    <div className="col-lg-6">
      <a href={href} className="text-primary float-right">
        {text}
      </a>
    </div>
  );
}

export default AuthLink;