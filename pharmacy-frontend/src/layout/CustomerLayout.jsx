import { Outlet } from "react-router-dom";

function CustomerLayout() {
    return (
        <>
            {/* Customer Navbar */}

            <Outlet />

            {/* Customer Footer */}
        </>
    );
}

export default CustomerLayout;