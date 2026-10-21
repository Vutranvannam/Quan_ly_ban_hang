export default function SidebarItem({ href, title, icon, active = false }) {
  return (
    <li className={active ? "active" : ""}>
      <a href={href} className="svg-icon">
        {icon}
        <span className="ml-4">{title}</span>
      </a>
    </li>
  );
}