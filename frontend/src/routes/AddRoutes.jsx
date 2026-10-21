import { Route } from "react-router-dom";
import AddProductPage from "../pages/add/AddProductPage";
import AddCategoryPage from "../pages/add/AddCategoryPage";
import AddExportPage from "../pages/add/AddExportPage";
import AddImportPage from "../pages/add/AddImportPage";
import AddReturnSupplierPage from "../pages/add/AddReturnSupplierPage";
import AddReturnCustomerPage from "../pages/add/AddReturnCustomerPage";
import AddInventoryAdjustmentPage from "../pages/add/AddInventoryAdjustmentPage";
import AddSupplierPage from "../pages/add/AddSupplierPage";
import AddCustomerPage from "../pages/add/AddCustomerPage";
import AddUserPage from "../pages/add/AddUserPage";
import PrivateRoute from "./PrivateRoute";

function AddRoutes() {
  return (
    <>
      <Route path="/product/add" element={
                                    <PrivateRoute>
                                      <AddProductPage />
                                    </PrivateRoute>
                                  }/>
      <Route path="/category/add" element={
                                    <PrivateRoute>
                                      <AddCategoryPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/partner/suppliers/add" element={
                                    <PrivateRoute>
                                      <AddSupplierPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/partner/customers/add" element={
                                    <PrivateRoute>
                                      <AddCustomerPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/user/add" element={
                                    <PrivateRoute>
                                      <AddUserPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/import/add" element={
                                    <PrivateRoute>
                                      <AddImportPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/export/add" element={
                                    <PrivateRoute>
                                      <AddExportPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/return/supplier/add" element={
                                    <PrivateRoute>
                                      <AddReturnSupplierPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/return/customer/add" element={
                                    <PrivateRoute>
                                      <AddReturnCustomerPage />
                                    </PrivateRoute>
                                  } />
      <Route path="/report/add" element={
                                    <PrivateRoute>
                                      <AddInventoryAdjustmentPage />
                                    </PrivateRoute>
                                  } />
    </>
  );
}

export default AddRoutes;