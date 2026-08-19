import VisibilityOutlinedIcon from "@mui/icons-material/VisibilityOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";

import {
    Button,
    Chip,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Tooltip,
    Typography
} from "@mui/material";


function getStatusColor(status) {
    if (status === "DELIVERED") return "success";
    if (status === "OUT_FOR_DELIVERY") return "warning";
    return "info";
}


function DeliveryOrderTable({
    deliveries,
    loading,
    onViewDetails,
    onUpdateStatus
}) {

    if (loading) {
        return (
            <Typography color="text.secondary">
                Loading deliveries...
            </Typography>
        );
    }


    if (!deliveries.length) {
        return (
            <Typography color="text.secondary">
                No deliveries found.
            </Typography>
        );
    }


    return (
        <TableContainer
            component={Paper}
            elevation={0}
            sx={{
                border: "1px solid #E5E7EB",
                borderRadius: 3
            }}
        >
            <Table>

                <TableHead>
                    <TableRow>

                        <TableCell>
                            <strong>Order ID</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Delivery Status</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Assigned Date</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Expected Delivery</strong>
                        </TableCell>

                        <TableCell align="center">
                            <strong>Actions</strong>
                        </TableCell>

                    </TableRow>
                </TableHead>


                <TableBody>

                    {deliveries.map(delivery => {

                        const status =
                            delivery.deliveryStatus;

                        const canUpdate =
                            status !== "DELIVERED";


                        return (
                            <TableRow
                                key={
                                    delivery.deliveryStatusId
                                }
                                hover
                            >

                                <TableCell>
                                    <Typography
                                        fontWeight={700}
                                    >
                                        #{delivery.orderId}
                                    </Typography>
                                </TableCell>


                                <TableCell>
                                    <Chip
                                        label={status}
                                        size="small"
                                        color={getStatusColor(
                                            status
                                        )}
                                    />
                                </TableCell>


                                <TableCell>
                                    {delivery.assignedDate
                                        ? new Date(
                                            delivery.assignedDate
                                        ).toLocaleString()
                                        : "—"}
                                </TableCell>


                                <TableCell>
                                    {delivery.expectedDeliveryDate
                                        ? new Date(
                                            delivery.expectedDeliveryDate
                                        ).toLocaleDateString()
                                        : "—"}
                                </TableCell>


                                <TableCell align="center">

                                    <Tooltip title="View Details">
                                        <IconButton
                                            onClick={() =>
                                                onViewDetails(
                                                    delivery.orderId
                                                )
                                            }
                                        >
                                            <VisibilityOutlinedIcon />
                                        </IconButton>
                                    </Tooltip>


                                    {canUpdate && (
                                        <Tooltip
                                            title={
                                                status ===
                                                "ASSIGNED"
                                                    ? "Start Delivery"
                                                    : "Mark Delivered"
                                            }
                                        >
                                            <IconButton
                                                color="primary"
                                                onClick={() =>
                                                    onUpdateStatus(
                                                        delivery
                                                    )
                                                }
                                            >
                                                <LocalShippingOutlinedIcon />
                                            </IconButton>
                                        </Tooltip>
                                    )}

                                </TableCell>

                            </TableRow>
                        );
                    })}

                </TableBody>

            </Table>
        </TableContainer>
    );
}
export default DeliveryOrderTable;