import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function Loader({ children }) {
  const location = useLocation();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);

    const timer = setTimeout(() => {
      setLoading(false);
    }, 400);

    return () => clearTimeout(timer);
  }, [location]);

  return (
    <>
      <div id="loading" style={{ display: loading ? "block" : "none" }}>
        <div id="loading-center"></div>
      </div>

      {children}
    </>
  );
}

export default Loader;