import { BrowserRouter} from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import Loader from "./components/Loader";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Loader>
          <AppRoutes />
        </Loader>
      </BrowserRouter> 
    </AuthProvider> 
  );
}

export default App;