import {
    Box,
    Typography
} from "@mui/material";

import useDeliveryBoyDashboard from "../../hooks/delivery/useDeliveryBoyDashboard";

import DashboardStats from "../../components/delivery/dashboard/DashboardStats";


function DeliveryBoyDashboard() {

    const {
        data,
        isLoading,
        isError
    } = useDeliveryBoyDashboard();


    const dashboard =
        data?.data;


    return (
        <Box>

            <Box sx={{ mb: 4 }}>

                <Typography
                    variant="h4"
                    fontWeight={700}
                    sx={{
                        color: "#263238",
                        mb: 0.5
                    }}
                >
                    Delivery Dashboard
                </Typography>

                <Typography
                    color="text.secondary"
                >
                    Overview of your delivery
                    activities.
                </Typography>

            </Box>


            <DashboardStats
                dashboard={dashboard}
                isLoading={isLoading}
                isError={isError}
            />

        </Box>
    );
}
export default DeliveryBoyDashboard;