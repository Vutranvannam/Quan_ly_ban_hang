import SelectSearch from "../item/SelectSearch";
import InputNumber from "../item/InputNumber";

export default function ExportRow({
  item,
  index,
  products,
  batches,      // backend trả về
  updateItem,
  removeRow
}) {

  const amount = item.quantity * item.price;

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
                onChange={(val) => {
                const selected = products.find(p => p.sku === val);

                if (selected) {
                    updateItem(index, "sku", selected.sku);
                    updateItem(index, "product", selected.title);
                }
                }}
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
                onChange={(val) => {
                const selected = products.find(p => p.title === val);

                if (selected) {
                    updateItem(index, "sku", selected.sku);
                    updateItem(index, "product", selected.title);
                }
                }}
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

        {/* Số lượng */}
        <td>
            <InputNumber
            value={item.quantity}
            min={1}
            onChange={(val) => updateItem(index, "quantity", val)}
            />
        </td>

        {/* Giá bán */}
        <td>
            <InputNumber
            value={item.price}
            min={0}
            onChange={(val) => updateItem(index, "price", val)}
            />
        </td>

        {/* Thành tiền */}
        <td>{amount.toLocaleString()} đ</td>

        {/* Xóa */}
        <td>
            <button className="btn bg-danger-light btn-rounded btn-sm" type="button" onClick={() => removeRow(index)}>X</button>
        </td>
    </tr>
  );
}