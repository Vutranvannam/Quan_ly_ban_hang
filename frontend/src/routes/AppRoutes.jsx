import { Routes } from "react-router-dom";
import AuthRoutes from "./AuthRoutes";
import DashBoardRoutes from "./DashBoardRoutes";
import AddRoutes from "./AddRoutes";
import UpdateRoutes from "./UpdateRoutes";
import ListRoutes from "./ListRoutes";

function AppRoutes() {
  return (
    <Routes>
      {AuthRoutes()}
      {DashBoardRoutes()}
      {AddRoutes()}
      {UpdateRoutes()}
      {ListRoutes()}
    </Routes>
  );
}

export default AppRoutes;