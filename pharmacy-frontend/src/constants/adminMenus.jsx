import AssessmentOutlinedIcon from "@mui/icons-material/AssessmentOutlined";
import CategoryOutlinedIcon from "@mui/icons-material/CategoryOutlined";
import DashboardOutlinedIcon from "@mui/icons-material/DashboardOutlined";
import Inventory2OutlinedIcon from "@mui/icons-material/Inventory2Outlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import LogoutOutlinedIcon from "@mui/icons-material/LogoutOutlined";
import MedicationOutlinedIcon from "@mui/icons-material/MedicationOutlined";
import PeopleOutlineIcon from "@mui/icons-material/PeopleOutlined";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import SummarizeOutlinedIcon from "@mui/icons-material/SummarizeOutlined";

export const ADMIN_MENUS = [

    {
        title: "Dashboard",
        path: "/admin/dashboard",
        icon: <DashboardOutlinedIcon />
    },

    {
        title: "Medicines",
        path: "/admin/medicines",
        icon: <MedicationOutlinedIcon />
    },

    {
        title: "Categories",
        path: "/admin/categories",
        icon: <CategoryOutlinedIcon />
    },

    {
        title: "Orders",
        path: "/admin/orders",
        icon: <Inventory2OutlinedIcon />
    },

    {
        title: "Prescriptions",
        path: "/admin/prescriptions",
        icon: <SummarizeOutlinedIcon />
    },

    {
        title: "Customers",
        path: "/admin/customers",
        icon: <PeopleOutlineIcon />
    },

    {
        title: "Delivery Boys",
        path: "/admin/delivery-boys",
        icon: <LocalShippingOutlinedIcon />
    },

    {
        title: "Returns",
        path: "/admin/returns",
        icon: <ReceiptLongOutlinedIcon />
    },

    {
        title: "Reports",
        path: "/admin/reports",
        icon: <AssessmentOutlinedIcon />
    },

    {
        title: "Settings",
        path: "/admin/settings",
        icon: <SettingsOutlinedIcon />
    }

];

export const LOGOUT_MENU = {

    title: "Logout",

    path: "/logout",

    icon: <LogoutOutlinedIcon />

};