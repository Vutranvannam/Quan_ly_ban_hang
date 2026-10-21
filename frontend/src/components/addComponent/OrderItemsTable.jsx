import { useState } from "react";

export default function OrderItemsTable({ products }) {

  const [items, setItems] = useState([
    {
      product_id: "",
      name: "",
      search: "",
      quantity: 1,
      price: 0,
      manufacture_date: "",
      expiry_date: "",
      showList: false
    }
  ]);

  const addRow = () => {
    setItems([
      ...items,
      {
        product_id: "",
        name: "",
        search: "",
        quantity: 1,
        price: 0,
        manufacture_date: "",
        expiry_date: "",
        showList: false
      }
    ]);
  };

  const removeRow = (index) => {
    const newItems = [...items];
    newItems.splice(index, 1);
    setItems(newItems);
  };

  const updateItem = (index, field, value) => {
    const newItems = [...items];
    newItems[index][field] = value;
    setItems(newItems);
  };

  const selectProduct = (index, product) => {
    const newItems = [...items];

    newItems[index].product_id = product.id;
    newItems[index].name = product.name;
    newItems[index].search = product.name;
    newItems[index].showList = false;

    setItems(newItems);
  };

  const total = items.reduce(
    (sum, item) => sum + item.quantity * item.price,
    0
  );

  return (
    <div className="col-md-12">

      <div className="card mt-3">

        <div className="card-header d-flex justify-content-between align-items-center">
          <h5 className="mb-0">Danh sách mặt hàng</h5>

          <button
            type="button"
            className="btn btn-sm btn-primary"
            onClick={addRow}
          >
            + Thêm mặt hàng
          </button>
        </div>

        <div className="card-body p-0">

          <table className="table table-hover mb-0">

            <thead className="table-light">
              <tr>
                <th style={{ width: "25%" }}>Sản phẩm</th>
                <th className="text-center">Số lượng</th>
                <th className="text-end">Đơn giá</th>
                <th>Ngày sản xuất</th>
                <th>Hạn sử dụng</th>
                <th className="text-end">Thành tiền</th>
                <th style={{ width: "80px" }}></th>
              </tr>
            </thead>

            <tbody>

              {items.map((item, index) => {

                const filteredProducts = products.filter((p) =>
                  p.name.toLowerCase().includes(item.search.toLowerCase())
                );

                const amount = item.quantity * item.price;

                return (

                  <tr key={index}>

                    {/* SEARCH PRODUCT */}
                    <td style={{ position: "relative" }}>

                      <input
                        type="text"
                        className="form-control"
                        placeholder="Tìm sản phẩm..."
                        value={item.search}
                        onChange={(e) => {

                          const newItems = [...items];
                          newItems[index].search = e.target.value;
                          newItems[index].showList = true;

                          setItems(newItems);

                        }}
                      />

                      {item.showList && item.search && (

                        <div
                          className="list-group"
                          style={{
                            position: "absolute",
                            zIndex: 10,
                            width: "100%",
                            maxHeight: "200px",
                            overflowY: "auto"
                          }}
                        >

                          {filteredProducts.map((p) => (

                            <button
                              key={p.id}
                              type="button"
                              className="list-group-item list-group-item-action"
                              onClick={() => selectProduct(index, p)}
                            >

                              {p.name}

                            </button>

                          ))}

                        </div>

                      )}

                    </td>

                    {/* QUANTITY */}
                    <td className="text-center">

                      <input
                        type="number"
                        className="form-control text-center"
                        value={item.quantity}
                        onChange={(e) =>
                          updateItem(index, "quantity", Number(e.target.value))
                        }
                      />

                    </td>

                    {/* PRICE */}
                    <td>

                      <input
                        type="number"
                        className="form-control text-end"
                        value={item.price}
                        onChange={(e) =>
                          updateItem(index, "price", Number(e.target.value))
                        }
                      />

                    </td>

                    {/* MANUFACTURE DATE */}
                    <td>

                      <input
                        type="date"
                        className="form-control"
                        value={item.manufacture_date}
                        onChange={(e) =>
                          updateItem(index, "manufacture_date", e.target.value)
                        }
                      />

                    </td>

                    {/* EXPIRY DATE */}
                    <td>

                      <input
                        type="date"
                        className="form-control"
                        value={item.expiry_date}
                        onChange={(e) =>
                          updateItem(index, "expiry_date", e.target.value)
                        }
                      />

                    </td>

                    {/* AMOUNT */}
                    <td className="text-end fw-bold">
                      {amount.toLocaleString()}
                    </td>

                    {/* REMOVE */}
                    <td className="text-center">

                      <button
                        type="button"
                        className="btn btn-sm btn-danger"
                        onClick={() => removeRow(index)}
                      >
                        ✕
                      </button>

                    </td>

                  </tr>

                );

              })}

            </tbody>

            <tfoot>

              <tr>
                <th colSpan="5" className="text-end">
                  Tổng tiền
                </th>

                <th className="text-end text-primary fs-5">
                  {total.toLocaleString()}
                </th>

                <th></th>
              </tr>

            </tfoot>

          </table>

        </div>

      </div>

    </div>
  );
}