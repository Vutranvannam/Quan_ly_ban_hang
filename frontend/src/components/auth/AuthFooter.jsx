import { Link } from "react-router-dom";

function AuthFooter({ text, linkText, href }) {
  return (
    <p className="mt-3">
      {text}{" "}
      <Link to={href} className="text-primary">
        {linkText}
      </Link>
    </p>
  );
}

export default AuthFooter;