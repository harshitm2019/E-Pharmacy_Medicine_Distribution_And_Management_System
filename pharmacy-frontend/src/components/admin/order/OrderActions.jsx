import CancelIcon from "@mui/icons-material/Cancel";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import InventoryIcon from "@mui/icons-material/Inventory";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { IconButton, Stack, Tooltip } from "@mui/material";

function OrderActions({
    order,
    onView,
    onOrderStatus,
    onPrescriptionStatus,
    onCancel
}) {
    const canConfirm =
        order.orderStatus === "PENDING" &&
        order.paymentStatus === "PAID" &&
        (
            order.prescription === null ||
            order.prescription?.status === "APPROVED"
        );

    return (
        <Stack direction="row" spacing={0.5} justifyContent="center">

            {/* View */}
            <Tooltip title="View Order">
                <IconButton
                    color="primary"
                    onClick={() => onView(order)}
                >
                    <VisibilityIcon />
                </IconButton>
            </Tooltip>

            {/* Prescription actions */}
            {order.orderStatus !== "CANCELLED" &&
                order.prescription?.status === "PENDING" && (
                    <>
                        <Tooltip title="Approve Prescription">
                            <IconButton
                                color="success"
                                onClick={() =>
                                    onPrescriptionStatus(order, "APPROVED")
                                }
                            >
                                <CheckCircleIcon />
                            </IconButton>
                        </Tooltip>

                        <Tooltip title="Reject Prescription">
                            <IconButton
                                color="error"
                                onClick={() =>
                                    onPrescriptionStatus(order, "REJECTED")
                                }
                            >
                                <CancelIcon />
                            </IconButton>
                        </Tooltip>
                    </>
                )}

            {/* Confirm Order */}
            {canConfirm && (
                <Tooltip title="Confirm Order">
                    <IconButton
                        color="success"
                        onClick={() =>
                            onOrderStatus(order, "CONFIRMED")
                        }
                    >
                        <CheckCircleIcon />
                    </IconButton>
                </Tooltip>
            )}

            {/* Mark Packed */}
            {order.orderStatus === "CONFIRMED" && (
                <Tooltip title="Mark Packed">
                    <IconButton
                        color="primary"
                        onClick={() =>
                            onOrderStatus(order, "PACKED")
                        }
                    >
                        <InventoryIcon />
                    </IconButton>
                </Tooltip>
            )}

            {/* Cancel */}
            {["PENDING", "CONFIRMED", "PACKED"].includes(order.orderStatus) && (
                <Tooltip title="Cancel Order">
                    <IconButton
                        color="error"
                        onClick={() => onCancel(order)}
                    >
                        <CancelIcon />
                    </IconButton>
                </Tooltip>
            )}
        </Stack>
    );
}
export default OrderActions;