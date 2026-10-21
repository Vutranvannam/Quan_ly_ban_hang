import { Route } from "react-router-dom";

import UpdateUserPage from "../pages/update/UpdateUserPage";
import UpdateCategoryPage from "../pages/update/UpdateCategoryPage";
import UpdateSupplierPage from "../pages/update/UpdateSupplierPage";
import UpdateCustomerPage from "../pages/update/UpdateCustomerPage";
import UpdateProductPage from "../pages/update/UpdateProductPage";

import PrivateRoute from "./PrivateRoute";

function UpdateRoutes() {
    return (
        <>
            <Route path="/user/update/:userId" element={
                                            <PrivateRoute>
                                                <UpdateUserPage />
                                            </PrivateRoute>
                                        }
            />

            <Route path="/category/update/:categoryId" element={
                                                <PrivateRoute>
                                                    <UpdateCategoryPage />
                                                </PrivateRoute>
                                            }
            />

            <Route path="/supplier/update/:supplierId" element={
                                                <PrivateRoute>
                                                    <UpdateSupplierPage />
                                                </PrivateRoute>
                                            }
            />

            <Route path="/customer/update/:customerId" element={
                                                <PrivateRoute>
                                                    <UpdateCustomerPage />
                                                </PrivateRoute>
                                            }
            />

            <Route path="/product/update/:productId" element={
                                                <PrivateRoute>
                                                    <UpdateProductPage />
                                                </PrivateRoute>
                                            }
            />
        </>
    )
}

export default UpdateRoutes