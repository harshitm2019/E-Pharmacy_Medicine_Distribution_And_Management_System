import AssessmentIcon from "@mui/icons-material/AssessmentOutlined";
import CategoryOutlinedIcon from "@mui/icons-material/CategoryOutlined";
import DashboardOutlinedIcon from "@mui/icons-material/DashboardOutlined";
import Inventory2OutlinedIcon from "@mui/icons-material/Inventory2Outlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import LogoutOutlinedIcon from "@mui/icons-material/LogoutOutlined";
import ManageAccountsOutlinedIcon from "@mui/icons-material/ManageAccountsOutlined";
import MedicationOutlinedIcon from "@mui/icons-material/MedicationOutlined";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";

export const ADMIN_MENUS = [
    { title: "Dashboard", path: "/admin/dashboard", icon: <DashboardOutlinedIcon /> },
    { title: "Medicines", path: "/admin/medicines", icon: <MedicationOutlinedIcon /> },
    { title: "Categories", path: "/admin/categories", icon: <CategoryOutlinedIcon /> },
    { title: "Orders", path: "/admin/orders", icon: <Inventory2OutlinedIcon /> },
    { title: "User Management", path: "/admin/user-management", icon: <ManageAccountsOutlinedIcon /> },
    { title: "Delivery Management", path: "/admin/delivery-boys", icon: <LocalShippingOutlinedIcon /> },
    { title: "Returns", path: "/admin/returns", icon: <ReceiptLongOutlinedIcon /> },
    { title: "Reports", path: "/admin/reports", icon: <AssessmentIcon /> },
    { title: "Settings", path: "/admin/settings", icon: <SettingsOutlinedIcon /> }
];

export const LOGOUT_MENU = { title: "Logout", path: "/logout", icon: <LogoutOutlinedIcon /> };