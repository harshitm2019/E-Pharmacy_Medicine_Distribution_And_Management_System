import { Box, Typography } from "@mui/material";
import DashboardStats from "../../components/customer/dashboard/DashboardStats";

function CustomerDashboard() {
    return (
        <Box>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>
                Dashboard
            </Typography>

            <Typography color="text.secondary" sx={{ mb: 4 }}>
                Overview of your pharmacy activity.
            </Typography>

            <DashboardStats />
        </Box>
    );
}

export default CustomerDashboard;