import CloseIcon from "@mui/icons-material/Close";

import {
    Box,
    Button,
    Chip,
    Dialog,
    DialogContent,
    DialogTitle,
    Divider,
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Select,
    Stack,
    Typography
} from "@mui/material";

import {
    useEffect,
    useState
} from "react";


function CustomerOrderDetailsDialog({
    open,
    order,
    payment,
    deliveryStatus,
    onClose,
    onPayment,
    onChangePaymentMethod,
    isChangingPaymentMethod
}) {

    const [
        paymentMethod,
        setPaymentMethod
    ] = useState(
        payment?.paymentMethod || ""
    );


    /*
     * Keep payment method synchronized
     * when a different order/payment is selected.
     */

    useEffect(() => {

        setPaymentMethod(
            payment?.paymentMethod || ""
        );

    }, [payment]);


    if (!order) {
        return null;
    }


    /*
     * ==========================================
     * PAYMENT CONDITIONS
     * ==========================================
     */

    const canChangePaymentMethod =
        order.orderStatus === "PENDING" &&
        order.paymentStatus === "PENDING";


    const canMakePayment =
        order.orderStatus === "PENDING" &&
        order.paymentStatus === "PENDING" &&
        payment &&
        payment.paymentMethod !== "COD";


    /*
     * ==========================================
     * ORDER TOTALS
     * ==========================================
     */

    const totalSubtotal =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(
                    item.subTotal ?? 0
                ),
            0
        ) || 0;


    const totalDiscount =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(
                    item.discount ?? 0
                ),
            0
        ) || 0;


    const totalTax =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(
                    item.tax ?? 0
                ),
            0
        ) || 0;


    /*
     * ==========================================
     * CHANGE PAYMENT METHOD
     * ==========================================
     */

    function handlePaymentMethodChange() {

        if (!paymentMethod) {
            return;
        }

        onChangePaymentMethod(
            order.orderId,
            paymentMethod
        );
    }


    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="md"
        >

            {/* ==================================
                TITLE
               ================================== */}

            <DialogTitle>

                <Typography
                    variant="h5"
                    fontWeight={700}
                >
                    Order #{order.orderId}
                </Typography>


                <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{ mt: 0.5 }}
                >
                    {order.orderDate
                        ? new Date(
                            order.orderDate
                        ).toLocaleString()
                        : "—"}
                </Typography>


                <IconButton
                    onClick={onClose}
                    sx={{
                        position:
                            "absolute",
                        right: 8,
                        top: 8
                    }}
                >
                    <CloseIcon />
                </IconButton>

            </DialogTitle>


            <DialogContent>

                <Stack spacing={3}>

                    {/* ==================================
                        STATUS
                       ================================== */}

                    <Box
                        sx={{
                            display: "grid",
                            gridTemplateColumns: {
                                xs: "1fr",
                                sm: "repeat(2, minmax(0, 1fr))"
                            },
                            gap: 2
                        }}
                    >

                        <StatusBox
                            title="Order Status"
                            status={
                                order.orderStatus
                            }
                            color={
                                getOrderStatusColor(
                                    order.orderStatus
                                )
                            }
                        />


                        <StatusBox
                            title="Payment Status"
                            status={
                                order.paymentStatus
                            }
                            color={
                                getPaymentStatusColor(
                                    order.paymentStatus
                                )
                            }
                        />

                    </Box>


                    {/* ==================================
                        ORDER INFORMATION
                       ================================== */}

                    <Box>

                        <Typography
                            variant="h6"
                            fontWeight={700}
                            sx={{ mb: 2 }}
                        >
                            Order Information
                        </Typography>


                        <Box
                            sx={{
                                display: "grid",
                                gridTemplateColumns: {
                                    xs: "1fr",
                                    sm: "repeat(2, minmax(0, 1fr))"
                                },
                                gap: 2
                            }}
                        >


                            <Box
                                sx={{
                                    gridColumn:
                                        "1 / -1"
                                }}
                            >

                                <InfoItem
                                    label="Shipping Address"
                                    value={
                                        order.shippingAddress
                                    }
                                />

                            </Box>

                        </Box>

                    </Box>


                    {/* ==================================
                        PAYMENT ACTIONS
                       ================================== */}

                    {(canChangePaymentMethod ||
                        canMakePayment) && (

                        <>

                            <Divider />


                            <Typography
                                variant="h6"
                                fontWeight={700}
                            >
                                Payment
                            </Typography>


                            <Stack
                                direction={{
                                    xs: "column",
                                    sm: "row"
                                }}
                                spacing={2}
                            >

                                {canChangePaymentMethod && (

                                    <FormControl
                                        size="small"
                                        sx={{
                                            minWidth: 220
                                        }}
                                    >

                                        <InputLabel>
                                            Payment Method
                                        </InputLabel>


                                        <Select
                                            value={
                                                paymentMethod
                                            }
                                            label="Payment Method"
                                            onChange={
                                                event =>
                                                    setPaymentMethod(
                                                        event
                                                            .target
                                                            .value
                                                    )
                                            }
                                        >

                                            <MenuItem value="UPI">
                                                UPI
                                            </MenuItem>


                                            <MenuItem value="DEBIT_CARD">
                                                Debit Card
                                            </MenuItem>


                                            <MenuItem value="CREDIT_CARD">
                                                Credit Card
                                            </MenuItem>


                                            <MenuItem value="COD">
                                                Cash on Delivery
                                            </MenuItem>

                                        </Select>

                                    </FormControl>

                                )}


                                {canChangePaymentMethod && (

                                    <Button
                                        variant="outlined"
                                        onClick={
                                            handlePaymentMethodChange
                                        }
                                        disabled={
                                            !paymentMethod ||
                                            isChangingPaymentMethod
                                        }
                                    >
                                        {isChangingPaymentMethod
                                            ? "Updating..."
                                            : "Update Payment Method"}
                                    </Button>

                                )}


                                {canMakePayment && (

                                    <Button
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

                            </Stack>

                        </>

                    )}


                    <Divider />


                    {/* ==================================
                        ORDER ITEMS
                       ================================== */}

                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Order Items
                    </Typography>


                    <Stack spacing={1.5}>

                        {order.items?.map(item => (

                            <Box
                                key={
                                    item.medicineId
                                }
                                sx={{
                                    p: 2,
                                    border:
                                        "1px solid #E5E7EB",
                                    borderRadius: 2
                                }}
                            >

                                <Typography
                                    fontWeight={700}
                                >
                                    {
                                        item.medicineName
                                    }
                                </Typography>


                                <Typography
                                    variant="body2"
                                    color="text.secondary"
                                    sx={{
                                        mt: 0.5
                                    }}
                                >
                                    Quantity:{" "}
                                    {
                                        item.quantity
                                    }
                                </Typography>


                                <Divider
                                    sx={{
                                        my: 1.5
                                    }}
                                />


                                {/* SINGLE ROW */}

                                <Box
                                    sx={{
                                        display: "grid",
                                        gridTemplateColumns:
                                            "repeat(3, minmax(0, 1fr))",
                                        gap: 2
                                    }}
                                >

                                    <InfoItem
                                        label="Subtotal"
                                        value={
                                            `₹${Number(
                                                item.subTotal ??
                                                    0
                                            ).toFixed(2)}`
                                        }
                                    />


                                    <InfoItem
                                        label="Discount"
                                        value={
                                            `₹${Number(
                                                item.discount ??
                                                    0
                                            ).toFixed(2)}`
                                        }
                                    />


                                    <InfoItem
                                        label="Tax"
                                        value={
                                            `₹${Number(
                                                item.tax ??
                                                    0
                                            ).toFixed(2)}`
                                        }
                                    />

                                </Box>

                            </Box>

                        ))}

                    </Stack>


                    {/* ==================================
                        ORDER SUMMARY
                       ================================== */}

                    <Box
                        sx={{
                            p: 2.5,
                            borderRadius: 2,
                            backgroundColor:
                                "#F8FAFC"
                        }}
                    >

                        <Typography
                            variant="h6"
                            fontWeight={700}
                            sx={{ mb: 2 }}
                        >
                            Order Summary
                        </Typography>


                        <Stack spacing={1.2}>

                            <SummaryRow
                                label="Total Subtotal"
                                value={
                                    `₹${totalSubtotal.toFixed(
                                        2
                                    )}`
                                }
                            />


                            <SummaryRow
                                label="Total Discount"
                                value={
                                    `₹${totalDiscount.toFixed(
                                        2
                                    )}`
                                }
                            />


                            <SummaryRow
                                label="Total Tax"
                                value={
                                    `₹${totalTax.toFixed(
                                        2
                                    )}`
                                }
                            />


                            <Divider />


                            <SummaryRow
                                label="Total Amount"
                                value={
                                    `₹${Number(
                                        order.totalAmount ??
                                            0
                                    ).toFixed(2)}`
                                }
                                strong
                            />

                        </Stack>

                    </Box>


                    {/* ==================================
                        PRESCRIPTION
                       ================================== */}

                    {order.prescription && (

                        <Box>

                            <Divider
                                sx={{ mb: 3 }}
                            />


                            <Typography
                                variant="h6"
                                fontWeight={700}
                                sx={{ mb: 2 }}
                            >
                                Prescription
                            </Typography>


                            <Box
                                sx={{
                                    p: 2,
                                    border:
                                        "1px solid #E5E7EB",
                                    borderRadius: 2
                                }}
                            >

                                <Stack spacing={1.2}>

                                    <InfoItem
                                        label="Prescription ID"
                                        value={
                                            order.prescription
                                                .prescriptionId
                                        }
                                    />


                                    <InfoItem
                                        label="Doctor"
                                        value={
                                            order.prescription
                                                .doctorName
                                        }
                                    />


                                    <InfoItem
                                        label="Status"
                                        value={
                                            order.prescription
                                                .status
                                        }
                                    />


                                    <InfoItem
                                        label="Uploaded"
                                        value={
                                            order.prescription
                                                .uploadedDate
                                                ? new Date(
                                                    order.prescription
                                                        .uploadedDate
                                                ).toLocaleString()
                                                : "—"
                                        }
                                    />

                                </Stack>

                            </Box>

                        </Box>

                    )}

                </Stack>

            </DialogContent>

        </Dialog>
    );
}


/*
 * ==========================================
 * STATUS BOX
 * ==========================================
 */

function StatusBox({
    title,
    status,
    color
}) {

    return (
        <Box
            sx={{
                p: 2,
                border:
                    "1px solid #E5E7EB",
                borderRadius: 2,
                backgroundColor:
                    "#FAFAFA"
            }}
        >

            <Typography
                variant="body2"
                color="text.secondary"
                sx={{ mb: 1 }}
            >
                {title}
            </Typography>


            <Chip
                label={status}
                size="small"
                color={color}
            />

        </Box>
    );
}


/*
 * ==========================================
 * INFO ITEM
 * ==========================================
 */

function InfoItem({
    label,
    value
}) {

    return (
        <Box>

            <Typography
                variant="body2"
                color="text.secondary"
            >
                {label}
            </Typography>


            <Typography
                fontWeight={600}
                sx={{
                    mt: 0.3,
                    wordBreak:
                        "break-word"
                }}
            >
                {value || "—"}
            </Typography>

        </Box>
    );
}


/*
 * ==========================================
 * SUMMARY ROW
 * ==========================================
 */

function SummaryRow({
    label,
    value,
    strong = false
}) {

    return (
        <Box
            sx={{
                display: "flex",
                justifyContent:
                    "space-between",
                alignItems: "center"
            }}
        >

            <Typography
                fontWeight={
                    strong ? 700 : 400
                }
            >
                {label}
            </Typography>


            <Typography
                fontWeight={
                    strong ? 800 : 600
                }
                fontSize={
                    strong ? 18 : 15
                }
            >
                {value}
            </Typography>

        </Box>
    );
}


/*
 * ==========================================
 * ORDER STATUS COLORS
 *
 * SAME COLORS AS ORDER CARD.
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
 * PAYMENT STATUS COLORS
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


export default CustomerOrderDetailsDialog;