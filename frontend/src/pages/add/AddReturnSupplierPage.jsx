import { useState } from "react";

import FormCard from "../../components/addComponent/FormCard";
import SelectField from "../../components/addComponent/SelectField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";

import HomeLayout from "../../layouts/HomeLayout";
import EditableTable from "../../components/editlist/EditableTable";
import ReturnRow from "../../components/editlist/row/ReturnRow";

export default function AddReturnSupplierPage() {

  /* ---------- DATA ---------- */
  const suppliers = ["Công ty ABC", "Cửa hàng Minh Phát", "Khách lẻ"];
  const statuses = ["DRAFT", "PENDING", "APPROVED", "COMPLETED", "CANCELLED"];
  const products = [
    { sku: "XML001", title: "Xi măng" },
    { sku: "XML002", title: "Thép" },
    { sku: "XML003", title: "Cát" }
  ];
  const batches = [
    { id: 1, batch_code: "BATCH001", stock: 50 },
    { id: 2, batch_code: "BATCH002", stock: 30 },
    { id: 3, batch_code: "BATCH003", stock: 100 }
  ];

  const returnColumns = [
    { key: "sku", title: "SKU" },
    { key: "product", title: "Sản phẩm" },
    { key: "batch", title: "Lô hàng" },
    { key: "quantity", title: "Số lượng" },
    { key: "reason", title: "Lý do trả" }
  ];

  /* ---------- STATE ---------- */
  const [items, setItems] = useState([{ sku: "", product: "", batch: "", quantity: 1, reason: "" }]);
  const [formData, setFormData] = useState({ supplier: "", status: "DRAFT", note: "" });

  /* ---------- HANDLER ---------- */
  const addRow = () => setItems(prev => [...prev, { sku: "", product: "", batch: "", quantity: 1, reason: "" }]);
  const removeRow = (index) => setItems(prev => prev.filter((_, i) => i !== index));
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
      setFormData({ supplier: "", status: "DRAFT", note: "" });
    }
  };

  /* ---------- UI ---------- */
  return (
    <HomeLayout>
      <FormCard title="Tạo phiếu trả nhà cung cấp">
        <form>

          {/* HEADER */}
          <div className="row">
            <SelectField label="Nhà cung cấp *" options={suppliers} col="col-md-6" required />
            <SelectField label="Trạng thái" options={statuses} col="col-md-6" />
            <TextAreaField label="Ghi chú" />
          </div>

          {/* TABLE */}
          <EditableTable title="Danh sách sản phẩm trả" names={returnColumns} addRow={addRow}>
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
          <div className="text-end mt-3">
            <FormButtons submitText="Tạo phiếu trả" addReset={resetForm} />
          </div>

        </form>
      </FormCard>
    </HomeLayout>
  );
}