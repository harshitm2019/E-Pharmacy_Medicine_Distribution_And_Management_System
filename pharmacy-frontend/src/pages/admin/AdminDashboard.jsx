import { Box, Typography } from "@mui/material";
import DashboardStats from "../../components/admin/dashboard/DashboardStats";

function AdminDashboard() {
    return (
        <Box>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>
                Dashboard
            </Typography>

            <Typography color="text.secondary" sx={{ mb: 4 }}>
                Overview of your pharmacy management system.
            </Typography>

            <DashboardStats />
        </Box>
    );
}

export default AdminDashboard;