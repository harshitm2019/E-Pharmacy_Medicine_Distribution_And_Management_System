import PrintOutlinedIcon from "@mui/icons-material/PrintOutlined";

import {
    Box,
    Button,
    Card,
    CardContent,
    Chip,
    Stack,
    Typography
} from "@mui/material";


function OrderCard({
    order,
    payment,
    onView,
    onCancel,
    onPayment,
    onPaymentReceipt,
    onTrack
}) {

    /*
     * ==========================================
     * CANCEL
     * ==========================================
     */

    const canCancel = [
        "PENDING",
        "CONFIRMED",
        "PACKED"
    ].includes(order.orderStatus);


    /*
     * ==========================================
     * MAKE PAYMENT
     * ==========================================
     *
     * Only pending online payment.
     *
     * COD does not show this button.
     * ==========================================
     */

    const canMakePayment =
        order.orderStatus === "PENDING" &&
        order.paymentStatus === "PENDING" &&
        payment &&
        payment.paymentMethod !== "COD";


    /*
     * ==========================================
     * PAYMENT RECEIPT
     * ==========================================
     */

    const canViewPaymentReceipt =
        order.paymentStatus === "PAID" &&
        payment?.paymentStatus === "SUCCESS";


    /*
     * ==========================================
     * TRACK DELIVERY
     * ==========================================
     *
     * Delivery tracking can be requested for
     * orders which have reached PACKED or later.
     *
     * The DeliveryStatusDialog itself handles
     * "Delivery Not Assigned Yet".
     * ==========================================
     */

    const canTrackDelivery =
        [
            "PACKED",
            "OUT_FOR_DELIVERY"
        ].includes(order.orderStatus);


    /*
     * ==========================================
     * ORDER STATUS COLOR
     *
     * KEEP THIS CONSISTENT EVERYWHERE.
     * ==========================================
     */

    function getOrderStatusColor(status) {

        switch (status) {

            case "PENDING":
                return "warning";

            case "CONFIRMED":
                return "info";

            case "PACKED":
                return "secondary";

            case "OUT_FOR_DELIVERY":
                return "primary";

            case "DELIVERED":
                return "success";

            case "CANCELLED":
                return "error";

            default:
                return "default";
        }
    }


    /*
     * ==========================================
     * PAYMENT STATUS COLOR
     * ==========================================
     */

    function getPaymentStatusColor(status) {

        switch (status) {

            case "PAID":
                return "success";

            case "PENDING":
                return "warning";

            case "FAILED":
                return "error";

            default:
                return "default";
        }
    }


    return (
        <Card
            elevation={0}
            sx={{
                height: "100%",
                border: "1px solid #E5E7EB",
                borderRadius: 3
            }}
        >

            <CardContent
                sx={{
                    p: 3,
                    "&:last-child": {
                        pb: 3
                    }
                }}
            >

                {/* ==================================
                    HEADER
                   ================================== */}

                <Box
                    sx={{
                        display: "flex",
                        justifyContent:
                            "space-between",
                        alignItems:
                            "flex-start",
                        gap: 2,
                        mb: 2
                    }}
                >

                    <Box>

                        <Typography
                            fontWeight={700}
                        >
                            Order #{order.orderId}
                        </Typography>


                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            {order.orderDate
                                ? new Date(
                                    order.orderDate
                                ).toLocaleString()
                                : "—"}
                        </Typography>

                    </Box>


                    <Chip
                        label={
                            order.orderStatus
                        }
                        size="small"
                        color={
                            getOrderStatusColor(
                                order.orderStatus
                            )
                        }
                    />

                </Box>


                {/* ==================================
                    ORDER INFORMATION
                   ================================== */}

                <Stack
                    spacing={1}
                    sx={{ mb: 2.5 }}
                >

                    <Typography>
                        <strong>
                            Total:
                        </strong>{" "}
                        ₹
                        {Number(
                            order.totalAmount ?? 0
                        ).toFixed(2)}
                    </Typography>


                    <Typography
                        sx={{
                            display: "flex",
                            alignItems: "center",
                            gap: 0.5,
                            flexWrap: "wrap"
                        }}
                    >
                        <strong>
                            Payment:
                        </strong>

                        <Chip
                            label={
                                order.paymentStatus ||
                                "—"
                            }
                            size="small"
                            color={
                                getPaymentStatusColor(
                                    order.paymentStatus
                                )
                            }
                        />
                    </Typography>


                    <Typography>
                        <strong>
                            Payment Method:
                        </strong>{" "}
                        {payment?.paymentMethod ||
                            "—"}
                    </Typography>



                </Stack>


                {/* ==================================
                    ACTION BUTTONS
                   ================================== */}

                <Box
                    sx={{
                        display: "flex",
                        flexWrap: "wrap",
                        gap: 1
                    }}
                >

                    {/* VIEW DETAILS */}

                    <Button
                        size="small"
                        variant="outlined"
                        onClick={() =>
                            onView(order)
                        }
                    >
                        View Details
                    </Button>


                    {/* MAKE PAYMENT */}

                    {canMakePayment && (

                        <Button
                            size="small"
                            variant="contained"
                            onClick={() =>
                                onPayment(
                                    order,
                                    payment
                                )
                            }
                        >
                            Make Payment
                        </Button>

                    )}


                    {/* PAYMENT RECEIPT */}

                    {canViewPaymentReceipt && (

                        <Button
                            size="small"
                            variant="outlined"
                            startIcon={
                                <PrintOutlinedIcon />
                            }
                            onClick={() =>
                                onPaymentReceipt(
                                    order
                                )
                            }
                        >
                            View / Print Payment Receipt
                        </Button>

                    )}


                    {/* TRACK DELIVERY */}

                    {canTrackDelivery && (

                        <Button
                            size="small"
                            variant="outlined"
                            onClick={() =>
                                onTrack(order)
                            }
                        >
                            Track Delivery
                        </Button>

                    )}


                    {/* CANCEL */}

                    {canCancel && (

                        <Button
                            size="small"
                            color="error"
                            variant="outlined"
                            onClick={() =>
                                onCancel(
                                    order.orderId
                                )
                            }
                        >
                            Cancel Order
                        </Button>

                    )}

                </Box>

            </CardContent>

        </Card>
    );
}


export default OrderCard;