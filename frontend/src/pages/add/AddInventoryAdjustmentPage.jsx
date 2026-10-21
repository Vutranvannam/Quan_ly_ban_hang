import { useState } from "react";
import FormCard from "../../components/addComponent/FormCard";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";

import HomeLayout from "../../layouts/HomeLayout";
import EditableTable from "../../components/editlist/EditableTable";
import InventoryAdjustmentRow from "../../components/editlist/row/InventoryAdjustmentRow";

export default function AddInventoryAdjustmentPage() {

  /* ---------- DATA ---------- */
  const products = [
    { sku: "XML001", title: "Xi măng" },
    { sku: "XML002", title: "Thép" },
    { sku: "XML003", title: "Cát" }
  ];

  const batches = []; // backend trả batch và system_quantity theo SKU/product

  const adjustmentColumns = [
    { key: "sku", title: "SKU" },
    { key: "product", title: "Sản phẩm" },
    { key: "batch", title: "Lô hàng" },
    { key: "system_quantity", title: "Tồn hệ thống" },
    { key: "actual_quantity", title: "Thực tế" },
    { key: "difference", title: "Chênh lệch" },
    { key: "reason", title: "Lý do" }
  ];

  /* ---------- STATE ---------- */
  const [items, setItems] = useState([
    { sku: "", product: "", batch: "", system_quantity: 0, actual_quantity: 0, reason: "" }
  ]);

  const [formData, setFormData] = useState({
    note: ""
  });

  /* ---------- HANDLERS ---------- */
  const addRow = () => {
    setItems(prev => [...prev, { sku: "", product: "", batch: "", system_quantity: 0, actual_quantity: 0, reason: "" }]);
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
      setItems([{ sku: "", product: "", batch: "", system_quantity: 0, actual_quantity: 0, reason: "" }]);
      setFormData({ note: "" });
    }
  };

  /* ---------- UI ---------- */
  return (
    <HomeLayout>
      <FormCard title="Tạo phiếu kiểm kho">
        <form>
          <TextAreaField label="Ghi chú" value={formData.note} onChange={(val) => setFormData({ note: val })} />

          {/* TABLE */}
          <EditableTable
            title="Danh sách sản phẩm kiểm kho"
            names={adjustmentColumns}
            addRow={addRow}
          >
            {items.map((item, index) => (
              <InventoryAdjustmentRow
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
            submitText="Tạo phiếu kiểm kho"
            addReset={resetForm}
          />
        </form>
      </FormCard>
    </HomeLayout>
  );
}