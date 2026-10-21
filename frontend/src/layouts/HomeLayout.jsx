import FooterHome from "../components/home/FooterHome";
import SidebarHome from "../components/home/sidebar/Sidebar";
import NavbarHome from "../components/home/Navbar";

function HomeLayout({ children }) {
    return (
        <div className="wrapper">
            <div className="iq-sidebar  sidebar-default ">
                <div className="iq-sidebar-logo d-flex align-items-center justify-content-between">
                    <a href="/" className="header-logo">
                        <img src="/assets/images/logo.png" className="img-fluid rounded-normal light-logo" alt="logo"/><h5 className="logo-title light-logo ml-3">POSDash</h5>
                    </a>
                    <div className="iq-menu-bt-sidebar ml-0">
                        <i className="las la-bars wrapper-menu"></i>
                    </div>
                </div>
                <SidebarHome />
            </div>
            <NavbarHome />
            {children}
            <FooterHome />   
        </div>
    );
}

export default HomeLayout;