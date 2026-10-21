import { Route } from "react-router-dom";
import ListProductPage from "../pages/list/ListProductPage";
import ListCategoryPage from "../pages/list/ListCategoryPage";
import ListImportPage from "../pages/list/ListImportPage";
import ListExportPage from "../pages/list/ListExportPage";
import ListSupplierPage from "../pages/list/ListSupplierPage";
import ListCustomerPage from "../pages/list/ListCustomerPage";
import ListUserPage from "../pages/list/ListUserPage";
import ListReturnSupplierPage from "../pages/list/ListReturnSupplierPage";
import ListReturnCustomerPage from "../pages/list/ListReturnCustomerPage";
import ListAdjustmentPage from "../pages/list/ListAdjustmentPage";
import PrivateRoute from "./PrivateRoute";


function ListRoutes() {
  return (
    <>
      <Route path="/product/list" element={
                                            <PrivateRoute>
                                              <ListProductPage />
                                            </PrivateRoute>
                                          }/>
      <Route path="/category/list" element={
                                            <PrivateRoute>
                                              <ListCategoryPage />
                                            </PrivateRoute>
                                          }/>

      <Route path="/partner/suppliers/list" element={
                                                    <PrivateRoute>
                                                      <ListSupplierPage />
                                                    </PrivateRoute>
                                                  }/>

      <Route path="/partner/customers/list" element={
                                                      <PrivateRoute>
                                                        <ListCustomerPage />
                                                      </PrivateRoute>
                                                    }/>
      <Route path="/user/list" element={
                                        <PrivateRoute>
                                          <ListUserPage />
                                        </PrivateRoute>
                                      }/>

      <Route path="/import/list" element={
                                        <PrivateRoute>
                                          <ListImportPage />
                                        </PrivateRoute>
                                      }/>

      <Route path="/export/list" element={
                                        <PrivateRoute>
                                          <ListExportPage />
                                        </PrivateRoute>
                                      }/>

      <Route path="/return/supplier/list" element={
                                        <PrivateRoute>
                                          <ListReturnSupplierPage />
                                        </PrivateRoute>
                                      }/>
      <Route path="/return/customer/list" element={
                                        <PrivateRoute>
                                          <ListReturnCustomerPage />
                                        </PrivateRoute>
                                      }/>
      <Route path="/report/list" element={
                                        <PrivateRoute>
                                          <ListAdjustmentPage />
                                        </PrivateRoute>
                                      }/>
    </>
  );
}

export default ListRoutes;