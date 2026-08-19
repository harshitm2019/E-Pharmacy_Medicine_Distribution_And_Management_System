import DashboardOutlinedIcon from "@mui/icons-material/DashboardOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import LogoutIcon from "@mui/icons-material/Logout";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";

export const DELIVERY_BOY_MENUS = [
    { title: "Dashboard", path: "/delivery/dashboard", icon: <DashboardOutlinedIcon /> },
    { title: "Deliveries", path: "/delivery/deliveries", icon: <LocalShippingOutlinedIcon /> },
    { title: "Settings", path: "/delivery/settings", icon: <SettingsOutlinedIcon /> }
];

export const LOGOUT_MENU = { title: "Logout", icon: <LogoutIcon /> };