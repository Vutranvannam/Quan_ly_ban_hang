import { Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import DashBoardPage from "../pages/DashBoardPage";

function DashBoardRoutes() {
  return (
    <>
      <Route path="/" element={
                        <PrivateRoute>
                          <DashBoardPage />
                        </PrivateRoute>
                      }/>
    </>
  );
}

export default DashBoardRoutes;