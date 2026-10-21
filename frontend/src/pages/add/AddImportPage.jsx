import { useState } from "react";

import FormCard from "../../components/addComponent/FormCard";
import InputField from "../../components/addComponent/InputField";
import SelectField from "../../components/addComponent/SelectField";
import TextAreaField from "../../components/addComponent/TextAreaField";
import FormButtons from "../../components/addComponent/FormButtons";

import HomeLayout from "../../layouts/HomeLayout";
import EditableTable from "../../components/editlist/EditableTable";
import ImportRow from "../../components/editlist/row/ImportRow";

export default function AddImportPage() {

  /* ---------- DATA ---------- */

  const suppliers = [
    "Khách lẻ",
    "Công ty ABC",
    "Cửa hàng Minh Phát"
  ];

  const statuses = [
    "Đang tạo",
    "Chờ duyệt",
    "Đã duyệt",
    "Đã nhập kho"
  ];

  const products = [
    { sku: "XML001", title: "Xi măng" },
    { sku: "XML002", title: "Thép" },
    { sku: "XML003", title: "Cát" }
  ];

  const importColumns = [
    { key: "sku", title: "SKU" },
    { key: "product", title: "Sản phẩm" },
    { key: "quantity", title: "Số lượng" },
    { key: "price", title: "Đơn giá" },
    { key: "mfg", title: "Ngày sản xuất" },
    { key: "exp", title: "Hạn sử dụng" },
    { key: "amount", title: "Thành tiền" }
  ];

  /* ---------- STATE ---------- */

  const [items, setItems] = useState([{ product: "", quantity: 1, price: 0, manufacture_date: "", expiry_date: ""}]);

  const [formData, setFormData] = useState({ date: "", supplier: "", status: "", note: "" });

  const addRow = () => {
    setItems(prev => [ ...prev,
      {
        product: "",
        quantity: 1,
        price: 0,
        manufacture_date: "",
        expiry_date: ""
      }
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
      setItems([
        {
          product: "",
          quantity: 1,
          price: 0,
          manufacture_date: "",
          expiry_date: ""
        }
      ]);

      setFormData({
        date: "",
        supplier: "",
        status: "",
        note: ""
      });
    }
  };

  /* ---------- UI ---------- */

  return (
    <HomeLayout>

      <FormCard title="Tạo phiếu nhập kho">

        <form>

          {/* FORM HEADER */}
          <div className="row">
            <SelectField label="Nhà cung cấp *" options={suppliers} col="col-md-6" required  />
            <SelectField label="Trạng thái" options={statuses} col="col-md-6" />
            <TextAreaField label="Ghi chú" />
          </div>

          {/* TABLE */}
          <EditableTable title="Danh sách sản phẩm" names={importColumns} addRow={addRow}>

            {items.map((item, index) => (
              <ImportRow key={index} item={item} index={index} products={products} updateItem={updateItem} removeRow={removeRow}/>
            ))}

          </EditableTable>


          {/* TOTAL */}
          <div className="text-end mt-3">
            <h5>Tổng tiền: {total.toLocaleString()} đ</h5>
          </div>

          {/* SUBMIT */}
          <FormButtons submitText="Tạo phiếu nhập" addReset={resetForm}/>

        </form>

      </FormCard>

    </HomeLayout>
  );
}