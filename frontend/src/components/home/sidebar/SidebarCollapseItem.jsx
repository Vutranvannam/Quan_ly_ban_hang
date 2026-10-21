import React from "react";

function SidebarCollapseItem({ id, title, icon, children , active = false, dataParent = "#iq-sidebar-toggle"}) {
  return (
    <li className={active ? "active" : ""}>
      <a href={`#${id}`} className="collapsed" data-toggle="collapse" aria-expanded={active ? "true" : "false"}>
        {icon}

        <span className="ml-4">{title}</span>

        <svg className="svg-icon iq-arrow-right arrow-active" width="20" height="20" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" >
          <polyline points="10 15 15 20 20 15"></polyline>
          <path d="M4 4h7a4 4 0 0 1 4 4v12"></path>
        </svg>
      </a>

      <ul id={id} className={`iq-submenu collapse ${active ? "show" : ""}`} data-parent={dataParent}>
        {children}
      </ul>
    </li>
  );
}

export default SidebarCollapseItem;