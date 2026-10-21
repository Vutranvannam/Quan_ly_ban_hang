import SelectSearch from "../item/SelectSearch";
import InputDate from "../item/InputDate";
import InputNumber from "../item/InputNumber";

export default function ImportRow({ item, index, products, updateItem, removeRow }) {
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

        {/* Số lượng */}
        <td>
            <InputNumber value={item.quantity} min={1} onChange={(val) => 
                                                            updateItem(index, "quantity", val)
                                                        }/>
        </td>

        {/* Đơn giá */}
        <td>
            <InputNumber value={item.price} min={0} onChange={(val) => 
                                                            updateItem(index, "price", val) 
                                                        }/>                                                  
        </td>
        {/* Ngày sản xuất */}
        <td>
            <InputDate value={item.manufacture_date} onChange={(val) => 
                                                        updateItem(index, "manufacture_date", val)
                                                    }/>
        </td>

        <td>
        <InputDate value={item.expiry_date} onChange={(val) =>
                                                        updateItem(index, "expiry_date", val)
                                                    }/>
        </td>

        {/* Thành tiền */}
        <td>{amount.toLocaleString()} đ</td>

        {/* Xóa */}
        <td>
            <button  className="btn bg-danger-light btn-rounded btn-sm my-0" type="button" onClick={() => removeRow(index)}>X</button>
        </td>
    </tr>
  );
}