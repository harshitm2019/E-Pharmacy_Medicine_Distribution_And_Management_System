import AssignmentTurnedInOutlinedIcon from "@mui/icons-material/AssignmentTurnedInOutlined";

import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";

import DirectionsCarFilledOutlinedIcon from "@mui/icons-material/DirectionsCarFilledOutlined";

import {
    Alert,
    Grid
} from "@mui/material";

import StatCard from "../../common/dashboard/StatCard";
import StatCardSkeleton from "../../common/dashboard/StatCardSkeleton";


function DashboardStats({
    dashboard,
    isLoading,
    isError
}) {

    if (isError) {

        return (
            <Alert severity="error">
                Unable to load dashboard data.
            </Alert>
        );
    }


    return (
        <Grid
            container
            spacing={3}
        >

            {/* Total Delivered Orders */}

            <Grid
                size={{
                    xs: 12,
                    sm: 6,
                    md: 4
                }}
            >

                {isLoading ? (

                    <StatCardSkeleton />

                ) : (

                    <StatCard
                        title="Total Delivered Orders"
                        value={
                            dashboard
                                ?.totalDeliveredOrders ??
                            0
                        }
                        icon={
                            <AssignmentTurnedInOutlinedIcon />
                        }
                        color="#2E7D32"
                    />

                )}

            </Grid>


            {/* Current Assigned Orders */}

            <Grid
                size={{
                    xs: 12,
                    sm: 6,
                    md: 4
                }}
            >

                {isLoading ? (

                    <StatCardSkeleton />

                ) : (

                    <StatCard
                        title="Current Assigned Orders"
                        value={
                            dashboard
                                ?.currentAssignedOrders ??
                            0
                        }
                        icon={
                            <LocalShippingOutlinedIcon />
                        }
                        color="#1976D2"
                    />

                )}

            </Grid>


            {/* Current Out For Delivery */}

            <Grid
                size={{
                    xs: 12,
                    sm: 6,
                    md: 4
                }}
            >

                {isLoading ? (

                    <StatCardSkeleton />

                ) : (

                    <StatCard
                        title="Current Out for Delivery"
                        value={
                            dashboard
                                ?.currentOutForDeliveryOrders ??
                            0
                        }
                        icon={
                            <DirectionsCarFilledOutlinedIcon />
                        }
                        color="#ED6C02"
                    />

                )}

            </Grid>

        </Grid>
    );
}


export default DashboardStats;