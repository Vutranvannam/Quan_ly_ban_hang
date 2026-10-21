export default function SvgIcon({ id, children, size = 20 }) {
  return (
    <svg className="svg-icon" id={id} width={size} height={size} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      {children}
    </svg>
  );
}