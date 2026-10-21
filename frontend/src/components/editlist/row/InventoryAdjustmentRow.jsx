import SelectSearch from "../item/SelectSearch";
import InputNumber from "../item/InputNumber";

export default function InventoryAdjustmentRow({ item, index, products, batches, updateItem, removeRow }) {

  const difference = (item.actual_quantity || 0) - (item.system_quantity || 0);

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
          options={batches} // backend trả batch tương ứng SKU/product
          value={item.batch}
          labelKey={(b) => `${b.batch_code} (tồn: ${b.system_quantity})`}
          valueKey="batch_code"
          placeholder="Chọn lô"
          onChange={(val) => updateItem(index, "batch", val)}
        />
      </td>

      {/* Số lượng hệ thống */}
      <td>
        <InputNumber
          value={item.system_quantity || 0}
          min={0}
          disabled
        />
      </td>

      {/* Số lượng thực tế */}
      <td>
        <InputNumber
          value={item.actual_quantity || 0}
          min={0}
          onChange={(val) => updateItem(index, "actual_quantity", val)}
        />
      </td>

      {/* Chênh lệch */}
      <td>{difference.toLocaleString()}</td>

      {/* Lý do */}
      <td>
        <input
          type="text"
          className="form-control"
          value={item.reason}
          placeholder="Lý do chênh lệch"
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