import SelectSearch from "../item/SelectSearch";
import InputNumber from "../item/InputNumber";

export default function ReturnRow({ item, index, products, batches, updateItem, removeRow }) {

  return (
    <tr>
      {/* SKU */}
      <td>
        <SelectSearch
          options={products}
          value={item.sku}
          labelKey="sku"
          valueKey="sku"
          placeholder="Chọn SKU"
          onChange={(val) => updateItem(index, "sku", val)}
        />
      </td>

      {/* Sản phẩm */}
      <td>
        <SelectSearch
          options={products}
          value={item.product}
          labelKey="title"
          valueKey="title"
          placeholder="Chọn sản phẩm"
          onChange={(val) => updateItem(index, "product", val)}
        />
      </td>

      {/* Lô hàng */}
      <td>
        <SelectSearch
          options={batches}
          value={item.batch}
          labelKey="batch_code"
          valueKey="batch_code"
          placeholder="Chọn lô"
          onChange={(val) => updateItem(index, "batch", val)}
        />
      </td>

      {/* Số lượng trả */}
      <td>
        <InputNumber
          value={item.quantity}
          min={1}
          onChange={(val) => updateItem(index, "quantity", val)}
        />
      </td>

      {/* Lý do trả */}
      <td>
        <input
          type="text"
          className="form-control"
          value={item.reason}
          placeholder="Lý do trả"
          onChange={(e) => updateItem(index, "reason", e.target.value)}
        />
      </td>

      {/* Xóa */}
      <td>
        <button
          className="btn bg-danger-light btn-rounded btn-sm"
          type="button"
          onClick={() => removeRow(index)}
        >
          X
        </button>
      </td>
    </tr>
  );
}