import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import CloseIcon from "@mui/icons-material/Close";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import PrintOutlinedIcon from "@mui/icons-material/PrintOutlined";

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


function PaymentReceipt({
    open,
    order,
    payment,
    onClose
}) {

    if (!order || !payment) {
        return null;
    }


    /*
     * ==========================================
     * TOTALS
     * ==========================================
     */

    const totalSubtotal =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(item.subTotal ?? 0),
            0
        ) || 0;


    const totalDiscount =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(item.discount ?? 0),
            0
        ) || 0;


    const totalTax =
        order.items?.reduce(
            (total, item) =>
                total +
                Number(item.tax ?? 0),
            0
        ) || 0;


    /*
     * ==========================================
     * PRINT
     * ==========================================
     */

    function handlePrint() {
        window.print();
    }


    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="md"
        >

            <DialogTitle
                className="no-print"
                sx={{
                    display: "flex",
                    alignItems: "center",
                    gap: 1
                }}
            >

                <IconButton
                    onClick={onClose}
                >
                    <ArrowBackIcon />
                </IconButton>


                <Typography
                    variant="h5"
                    fontWeight={700}
                >
                    Payment Receipt
                </Typography>


                <IconButton
                    onClick={onClose}
                    sx={{
                        ml: "auto"
                    }}
                >
                    <CloseIcon />
                </IconButton>

            </DialogTitle>


            <DialogContent>

                {/* ==================================
                    PRINTABLE RECEIPT
                   ================================== */}

                <Box
                    id="print-bill"
                    sx={{
                        maxWidth: 850,
                        mx: "auto",
                        backgroundColor: "white"
                    }}
                >

                    {/* PHARMACY HEADER */}

                    <Box
                        sx={{
                            textAlign: "center",
                            mb: 2
                        }}
                    >

                        <LocalPharmacyIcon
                            sx={{
                                fontSize: 64,
                                color: "success.main"
                            }}
                        />


                        <Typography
                            variant="h5"
                            fontWeight={800}
                        >
                            Pharmacy
                        </Typography>


                        <Typography
                            variant="h6"
                            fontWeight={700}
                            sx={{
                                mt: 1
                            }}
                        >
                            PAYMENT RECEIPT
                        </Typography>


                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            Payment confirmation
                        </Typography>

                    </Box>

                     {/* SHIPPING ADDRESS */}

                    <InfoItem
                        label="Shipping Address"
                        value={
                            order.shippingAddress
                        }
                    />


                    <Divider
                        sx={{
                            my: 3
                        }}
                    />


                    {/* PAYMENT INFORMATION */}

                    <Typography
                        variant="h6"
                        fontWeight={700}
                        sx={{
                            mb: 2
                        }}
                    >
                        Payment Information
                    </Typography>


                    <Box
                        sx={{
                            display: "grid",
                            gridTemplateColumns:
                                "repeat(2, minmax(0, 1fr))",
                            gap: 2,
                            mb: 3
                        }}
                    >

                        <InfoItem
                            label="Order ID"
                            value={
                                `#${order.orderId}`
                            }
                        />


                        <InfoItem
                            label="Payment ID"
                            value={
                                payment.paymentId
                            }
                        />


                        <InfoItem
                            label="Order Date"
                            value={
                                order.orderDate
                                    ? new Date(
                                        order.orderDate
                                    ).toLocaleString()
                                    : "—"
                            }
                        />


                        <InfoItem
                            label="Paid Date"
                            value={
                                payment.paidDate
                                    ? new Date(
                                        payment.paidDate
                                    ).toLocaleString()
                                    : "—"
                            }
                        />


                        <InfoItem
                            label="Payment Method"
                            value={
                                payment.paymentMethod
                            }
                        />


                        <InfoItem
                            label="Payment Status"
                            value={
                                payment.paymentStatus
                            }
                        />

                    </Box>


                    <Divider
                        sx={{
                            mb: 3
                        }}
                    />


                    {/* ORDER ITEMS */}

                    <Typography
                        variant="h6"
                        fontWeight={700}
                        sx={{
                            mb: 2
                        }}
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


                    <Divider
                        sx={{
                            my: 3
                        }}
                    />


                    {/* SUMMARY */}

                    <Box
                        sx={{
                            maxWidth: 400,
                            ml: "auto"
                        }}
                    >

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


                        <Divider
                            sx={{
                                my: 1.5
                            }}
                        />


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

                    </Box>


                    <Divider
                        sx={{
                            my: 3
                        }}
                    />


                   

                </Box>


                {/* PRINT BUTTON */}

                <Box
                    className="no-print"
                    sx={{
                        display: "flex",
                        justifyContent:
                            "center",
                        mt: 3
                    }}
                >

                    <Button
                        variant="contained"
                        startIcon={
                            <PrintOutlinedIcon />
                        }
                        onClick={
                            handlePrint
                        }
                    >
                        Print Receipt
                    </Button>

                </Box>

            </DialogContent>

        </Dialog>
    );
}


/* ==========================================
   INFO ITEM
   ========================================== */

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
                    wordBreak: "break-word"
                }}
            >
                {value || "—"}
            </Typography>

        </Box>
    );
}


/* ==========================================
   SUMMARY ROW
   ========================================== */

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

export default PaymentReceipt;