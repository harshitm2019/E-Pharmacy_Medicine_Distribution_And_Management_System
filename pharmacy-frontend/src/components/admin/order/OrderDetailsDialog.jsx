import CloseIcon from "@mui/icons-material/Close";
import {
    Box,
    Button,
    Dialog,
    DialogContent,
    DialogTitle,
    Divider,
    IconButton,
    Stack,
    Typography
} from "@mui/material";

import useDeliveryStatus from "../../../hooks/admin/useDeliveryStatus";

function OrderDetailsDialog({
    open,
    order,
    onClose,
    onAssign
}) {

    if (!order) {
        return null;
    }

    const {
        data: deliveryData,
        isLoading: deliveryLoading
    } = useDeliveryStatus(
        order.orderId,
        open
    );

    const delivery = deliveryData?.data;

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="md"
        >

            <DialogTitle>

                Order #{order.orderId}

                <IconButton
                    onClick={onClose}
                    sx={{
                        position: "absolute",
                        right: 8,
                        top: 8
                    }}
                >
                    <CloseIcon />
                </IconButton>

            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>

                <Stack spacing={2}>

                    {/* ORDER INFORMATION */}

                    <Box>

                        <Typography>
                            <strong>
                                Order Date:
                            </strong>{" "}
                            {order.orderDate}
                        </Typography>

                        <Typography>
                            <strong>
                                Order Status:
                            </strong>{" "}
                            {order.orderStatus}
                        </Typography>

                        <Typography>
                            <strong>
                                Payment Status:
                            </strong>{" "}
                            {order.paymentStatus}
                        </Typography>

                        <Typography>
                            <strong>
                                Shipping Address:
                            </strong>{" "}
                            {order.shippingAddress}
                        </Typography>

                    </Box>

                    {/* ASSIGN DELIVERY BOY */}

                    {order.orderStatus === "PACKED" &&
                        delivery?.deliveryStatus !==
                            "ASSIGNED" && (

                        <Button
                            variant="contained"
                            onClick={() =>
                                onAssign(
                                    order.orderId
                                )
                            }
                            disabled={
                                deliveryLoading
                            }
                        >
                            Assign Delivery Boy
                        </Button>

                    )}

                    <Divider />

                    {/* ORDER ITEMS */}

                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Order Items
                    </Typography>

                    {order.items?.map(item => (

                        <Box
                            key={item.medicineId}
                            sx={{
                                p: 2,
                                border:
                                    "1px solid #E5E7EB",
                                borderRadius: 2
                            }}
                        >

                            <Typography
                                fontWeight={600}
                            >
                                {item.medicineName}
                            </Typography>

                            <Typography>
                                Quantity:{" "}
                                {item.quantity}
                            </Typography>

                            <Typography>
                                Subtotal: ₹
                                {Number(
                                    item.subTotal ?? 0
                                ).toFixed(2)}
                            </Typography>

                            <Typography>
                                Discount: ₹
                                {Number(
                                    item.discount ?? 0
                                ).toFixed(2)}
                            </Typography>

                            <Typography>
                                Tax: ₹
                                {Number(
                                    item.tax ?? 0
                                ).toFixed(2)}
                            </Typography>

                        </Box>

                    ))}

                    <Divider />

                    {/* TOTAL */}

                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Total Amount: ₹
                        {Number(
                            order.totalAmount ?? 0
                        ).toFixed(2)}
                    </Typography>

                    {/* PRESCRIPTION */}

                    {order.prescription && (

                        <>
                            <Divider />

                            <Typography
                                variant="h6"
                                fontWeight={700}
                            >
                                Prescription
                            </Typography>

                            <Typography>
                                <strong>
                                    Prescription ID:
                                </strong>{" "}
                                {
                                    order.prescription
                                        .prescriptionId
                                }
                            </Typography>

                            <Typography>
                                <strong>
                                    Doctor:
                                </strong>{" "}
                                {
                                    order.prescription
                                        .doctorName
                                }
                            </Typography>

                            <Typography>
                                <strong>
                                    Status:
                                </strong>{" "}
                                {
                                    order.prescription
                                        .status
                                }
                            </Typography>

                            <Typography>
                                <strong>
                                    Uploaded:
                                </strong>{" "}
                                {
                                    order.prescription
                                        .uploadedDate
                                }
                            </Typography>

                            {order.prescription
                                .prescriptionUrl && (

                                <Typography>

                                    <a
                                        href={
                                            order.prescription
                                                .prescriptionUrl
                                        }
                                        target="_blank"
                                        rel="noreferrer"
                                    >
                                        View Prescription
                                    </a>

                                </Typography>

                            )}

                        </>

                    )}

                </Stack>

            </DialogContent>

        </Dialog>
    );
}
export default OrderDetailsDialog;