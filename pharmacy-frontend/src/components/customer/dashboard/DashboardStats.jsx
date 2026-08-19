import {
    AssignmentOutlined,
    LocalShippingOutlined,
    PendingActionsOutlined,
    ReceiptLongOutlined,
    ReplayOutlined,
    ShoppingBagOutlined
} from "@mui/icons-material";
import { Grid } from "@mui/material";

import useCustomerDashboard from "../../../hooks/customer/useCustomerDashboard";
import StatCard from "../../common/dashboard/StatCard";
import StatCardSkeleton from "../../common/dashboard/StatCardSkeleton";

function DashboardStats() {
    const { data, isLoading } = useCustomerDashboard();

    if (isLoading) {
        return (
            <Grid container spacing={3}>
                {Array.from({ length: 6 }).map((_, index) => (
                    <Grid key={index} size={{ xs: 12, sm: 6, lg: 4 }}>
                        <StatCardSkeleton />
                    </Grid>
                ))}
            </Grid>
        );
    }

    const dashboard = data?.data;

    const stats = [
        {
            title: "Total Orders",
            value: dashboard?.totalOrders ?? 0,
            icon: <ShoppingBagOutlined />,
            color: "#2E7D32"
        },
        {
            title: "Pending Orders",
            value: dashboard?.pendingOrders ?? 0,
            icon: <PendingActionsOutlined />,
            color: "#EF6C00"
        },
        {
            title: "Delivered Orders",
            value: dashboard?.deliveredOrders ?? 0,
            icon: <LocalShippingOutlined />,
            color: "#1565C0"
        },
        {
            title: "Total Returns",
            value: dashboard?.totalReturns ?? 0,
            icon: <ReplayOutlined />,
            color: "#D32F2F"
        },
        {
            title: "Prescriptions Uploaded",
            value: dashboard?.totalPrescriptions ?? 0,
            icon: <AssignmentOutlined />,
            color: "#7B1FA2"
        },
        {
            title: "Pending Payments",
            value: dashboard?.pendingPayments ?? 0,
            icon: <ReceiptLongOutlined />,
            color: "#00897B"
        }
    ];

    return (
        <Grid container spacing={3}>
            {stats.map(stat => (
                <Grid key={stat.title} size={{ xs: 12, sm: 6, lg: 4 }}>
                    <StatCard {...stat} />
                </Grid>
            ))}
        </Grid>
    );
}
export default DashboardStats;