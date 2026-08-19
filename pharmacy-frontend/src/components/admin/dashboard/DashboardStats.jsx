import GroupsOutlinedIcon from "@mui/icons-material/GroupsOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import MedicationOutlinedIcon from "@mui/icons-material/MedicationOutlined";
import PendingActionsOutlinedIcon from "@mui/icons-material/PendingActionsOutlined";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import WidgetsOutlinedIcon from "@mui/icons-material/WidgetsOutlined";
import StatCardSkeleton from "../../common/dashboard/StatCardSkeleton";

import { Grid } from "@mui/material";

import useDashboard from "../../../hooks/admin/useDashboard";
import StatCard from "../../common/dashboard/StatCard";

function DashboardStats() {

    const { data, isLoading } = useDashboard();

    if (isLoading) {

        return (

        <Grid container spacing={3}>

            {
                Array.from({ length: 6 }).map((_, index) => (

                    <Grid
                        key={index}
                        size={{ xs: 12, sm: 6, lg: 4 }}
                    >
                        <StatCardSkeleton />

                    </Grid>

                ))
            }

        </Grid>

    );

    }

    const dashboard = data.data;

    const stats = [

        {
            title: "Active Medicines",
            value: dashboard.activeMedicines,
            icon: <MedicationOutlinedIcon />,
            color: "#2E7D32"
        },

        {
            title: "Categories",
            value: dashboard.totalCategories,
            icon: <WidgetsOutlinedIcon />,
            color: "#1565C0"
        },

        {
            title: "Active Customers",
            value: dashboard.activeCustomers,
            icon: <GroupsOutlinedIcon />,
            color: "#7B1FA2"
        },

        {
            title: "Delivery Boys",
            value: dashboard.activeDeliveryBoys,
            icon: <LocalShippingOutlinedIcon />,
            color: "#EF6C00"
        },

        {
            title: "Pending Orders",
            value: dashboard.pendingOrders,
            icon: <PendingActionsOutlinedIcon />,
            color: "#D32F2F"
        },

        {
            title: "Pending Prescriptions",
            value: dashboard.pendingPrescriptions,
            icon: <ReceiptLongOutlinedIcon />,
            color: "#00897B"
        }

    ];

    return (

        <Grid container spacing={3}>

            {

                stats.map(stat => (

                    <Grid
                        key={stat.title}
                        size={{ xs: 12, sm: 6, lg: 4 }}
                    >

                        <StatCard
                            title={stat.title}
                            value={stat.value}
                            icon={stat.icon}
                            color={stat.color}
                        />

                    </Grid>

                ))
            }
        </Grid>
    );
}
export default DashboardStats;