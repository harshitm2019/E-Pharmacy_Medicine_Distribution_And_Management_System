import AssignmentOutlinedIcon from "@mui/icons-material/AssignmentOutlined";
import DashboardOutlinedIcon from "@mui/icons-material/DashboardOutlined";
import LocalPharmacyOutlinedIcon from "@mui/icons-material/LocalPharmacyOutlined";
import LogoutIcon from "@mui/icons-material/Logout";
import PaymentOutlinedIcon from "@mui/icons-material/PaymentOutlined";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import ReplayOutlinedIcon from "@mui/icons-material/ReplayOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";

export const CUSTOMER_MENUS = [
    { title: "Dashboard", path: "/customer/dashboard", icon: <DashboardOutlinedIcon /> },
    { title: "Medicines", path: "/customer/medicines", icon: <LocalPharmacyOutlinedIcon /> },
    { title: "Cart", path: "/customer/cart", icon: <ShoppingCartOutlinedIcon /> },
    { title: "Prescriptions", path: "/customer/prescriptions", icon: <AssignmentOutlinedIcon /> },
    { title: "My Orders", path: "/customer/orders", icon: <ReceiptLongOutlinedIcon /> },
    { title: "My Returns", path: "/customer/returns", icon: <ReplayOutlinedIcon /> },
    { title: "My Payments", path: "/customer/payments", icon: <PaymentOutlinedIcon /> },
    { title: "Settings", path: "/customer/settings", icon: <SettingsOutlinedIcon /> }
];

export const LOGOUT_MENU = {
    title: "Logout",
    icon: <LogoutIcon />
};