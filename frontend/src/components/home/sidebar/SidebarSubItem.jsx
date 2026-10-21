import React from "react";

function SidebarSubItem({ href, title }) {
  return (
    <li>
      <a href={href}>
        <i className="las la-minus"></i>
        <span>{title}</span>
      </a>
    </li>
  );
}

export default SidebarSubItem;