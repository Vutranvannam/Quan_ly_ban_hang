import { useState } from "react";
import FormCard from "../../components/addComponent/FormCard";
import SelectField from "../../components/addComponent/SelectField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";

import HomeLayout from "../../layouts/HomeLayout";
import EditableTable from "../../components/editlist/EditableTable";
import ReturnRow from "../../components/editlist/row/ReturnRow";

export default function AddReturnCustomerPage() {

  /* ---------- DATA ---------- */
  const orders = [
    { id: 1, code: "SO001" },
    { id: 2, code: "SO002" }
  ];

  const products = [
    { sku: "XML001", title: "Xi măng" },
    { sku: "XML002", title: "Thép" },
    { sku: "XML003", title: "Cát" }
  ];

  // Batches sẽ được backend trả về dựa trên SKU/product
  const batches = [];

  const statuses = [
    "DRAFT",
    "PENDING",
    "APPROVED",
    "COMPLETED",
    "CANCELLED"
  ];

  const returnColumns = [
    { key: "sku", title: "SKU" },
    { key: "product", title: "Sản phẩm" },
    { key: "batch", title: "Lô hàng" },
    { key: "quantity", title: "Số lượng trả" },
    { key: "reason", title: "Lý do" }
  ];

  /* ---------- STATE ---------- */
  const [items, setItems] = useState([
    { sku: "", product: "", batch: "", quantity: 1, reason: "" }
  ]);

  const [formData, setFormData] = useState({
    sales_order: "",
    status: "DRAFT",
    note: ""
  });

  /* ---------- HANDLERS ---------- */
  const addRow = () => {
    setItems(prev => [...prev, { sku: "", product: "", batch: "", quantity: 1, reason: "" }]);
  };

  const removeRow = (index) => {
    setItems(prev => prev.filter((_, i) => i !== index));
  };

  const updateItem = (index, field, value) => {
    setItems(prev => {
      const newItems = [...prev];
      newItems[index] = { ...newItems[index], [field]: value };
      return newItems;
    });
  };

  const resetForm = () => {
    if (window.confirm("Bạn có chắc muốn reset?")) {
      setItems([{ sku: "", product: "", batch: "", quantity: 1, reason: "" }]);
      setFormData({ sales_order: "", status: "DRAFT", note: "" });
    }
  };

  /* ---------- UI ---------- */
  return (
    <HomeLayout>
      <FormCard title="Tạo phiếu trả hàng khách">
        <form>
          {/* HEADER */}
          <div className="row">
            <SelectField
              label="Đơn hàng *"
              options={orders.map(o => o.code)}
              col="col-md-6"
              required
            />

            <SelectField
              label="Trạng thái"
              options={statuses}
              col="col-md-6"
            />

            <TextAreaField label="Ghi chú" />
          </div>

          {/* TABLE */}
          <EditableTable
            title="Danh sách sản phẩm trả"
            names={returnColumns}
            addRow={addRow}
          >
            {items.map((item, index) => (
              <ReturnRow
                key={index}
                item={item}
                index={index}
                products={products}
                batches={batches}
                updateItem={updateItem}
                removeRow={removeRow}
              />
            ))}
          </EditableTable>

          {/* BUTTON */}
          <FormButtons
            submitText="Tạo phiếu trả"
            addReset={resetForm}
          />
        </form>
      </FormCard>
    </HomeLayout>
  );
}