import { useState } from "react";

import FormCard from "../../components/addComponent/FormCard";
import SelectField from "../../components/addComponent/SelectField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";

import HomeLayout from "../../layouts/HomeLayout";
import EditableTable from "../../components/editlist/EditableTable";
import ExportRow from "../../components/editlist/row/ExportRow";

export default function AddExportPage() {

  /* ---------- DATA ---------- */

  const customers = [
    "Khách lẻ",
    "Công ty XYZ",
    "Cửa hàng Hoàng Long"
  ];

  const statuses = [
    "DRAFT",
    "PENDING",
    "APPROVED",
    "COMPLETED",
    "CANCELLED"
  ];

  const products = [
    { sku: "XML001", title: "Xi măng" },
    { sku: "XML002", title: "Thép" },
    { sku: "XML003", title: "Cát" }
  ];

  const exportColumns = [
    { key: "sku", title: "SKU" },
    { key: "product", title: "Sản phẩm" },
    { key: "batch", title: "Lô hàng" },
    { key: "quantity", title: "Số lượng" },
    { key: "price", title: "Giá bán" },
    { key: "amount", title: "Thành tiền" }
  ];

  const batches = [
    {
      id: 1,
      product: "Xi măng",
      batch_code: "BATCH001",
      stock: 50
    },
    {
      id: 2,
      product: "Xi măng",
      batch_code: "BATCH002",
      stock: 30
    },
    {
      id: 3,
      product: "Thép",
      batch_code: "BATCH003",
      stock: 20
    },
    {
      id: 4,
      product: "Cát",
      batch_code: "BATCH004",
      stock: 10
    }
  ];

  /* ---------- STATE ---------- */

  const [items, setItems] = useState([
    { product: "", sku: "", batch: "", quantity: 1, price: 0 }
  ]);

  const [formData, setFormData] = useState({
    customer: "",
    status: "DRAFT",
    note: ""
  });

  /* ---------- HANDLER ---------- */


  const addRow = () => {
    setItems(prev => [
      ...prev,
      { product: "", sku: "", batch: "", quantity: 1, price: 0 }
    ]);
  };

  const removeRow = (index) => {
    setItems(prev => prev.filter((_, i) => i !== index));
  };

  const updateItem = (index, field, value) => {
    setItems(prev => {
      const newItems = [...prev];
      newItems[index] = {
        ...newItems[index],
        [field]: value
      };
      return newItems;
    });
  };

  const total = items.reduce(
    (sum, item) => sum + item.quantity * item.price,
    0
  );

  const resetForm = () => {
    if (window.confirm("Bạn có chắc muốn reset?")) {
      setItems([{ product: "", sku: "", batch: "", quantity: 1, price: 0 }]);
      setFormData({
        customer: "",
        status: "DRAFT",
        note: ""
      });
    }
  };

  /* ---------- UI ---------- */

  return (
    <HomeLayout>

      <FormCard title="Tạo phiếu xuất kho">

        <form>

          {/* HEADER */}
          <div className="row">
            <SelectField
              label="Khách hàng *"
              options={customers}
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
          <EditableTable title="Danh sách sản phẩm" names={exportColumns} addRow={addRow}>
            {items.map((item, index) => (
              <ExportRow key={index} item={item} index={index} products={products} batches={batches} updateItem={updateItem} removeRow={removeRow}/>
            ))}
          </EditableTable>

          {/* TOTAL */}
          <div className="text-end mt-3">
            <h5>Tổng tiền: {total.toLocaleString()} đ</h5>
          </div>

          {/* BUTTON */}
          <FormButtons
            submitText="Tạo phiếu xuất"
            addReset={resetForm}
          />

        </form>

      </FormCard>

    </HomeLayout>
  );
}