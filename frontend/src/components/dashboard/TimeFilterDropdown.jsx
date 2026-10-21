export default function TimeFilterDropdown({ id = "dropdownMenu", label = "This Month" }) {
  return (
    <div className="card-header-toolbar d-flex align-items-center">
      <div className="dropdown">
        <span className="dropdown-toggle dropdown-bg btn" id={id} data-toggle="dropdown">
          {label}
          <i className="ri-arrow-down-s-line ml-1"></i>
        </span>

        <div className="dropdown-menu dropdown-menu-right shadow-none" aria-labelledby={id}>
          <a className="dropdown-item" href="#">Year</a>
          <a className="dropdown-item" href="#">Month</a>
          <a className="dropdown-item" href="#">Week</a>
        </div>
      </div>
    </div>
  );
}