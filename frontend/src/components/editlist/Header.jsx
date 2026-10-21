export default function Header({ title }) {
    return (
        <div className="card-header d-flex justify-content-between">
            <div className="header-title">
                <h4 className="card-title">{title}</h4>
            </div>
        </div>
    );
}