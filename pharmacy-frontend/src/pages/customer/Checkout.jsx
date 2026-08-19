import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Divider,
    FormLabel,
    Stack,
    TextField,
    Typography
} from "@mui/material";

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

import useCart from "../../hooks/customer/useCart";
import useCheckout from "../../hooks/customer/useCheckout";
import useOnlinePayment from "../../hooks/customer/useOnlinePayment";
import useUploadPrescription from "../../hooks/customer/useUploadPrescription";

function Checkout() {

    const navigate = useNavigate();

    const {
        cart,
        totalItems,
        totalAmount,
        hasPrescriptionMedicine,
        clearCart
    } = useCart();

    const checkout = useCheckout();
    const onlinePayment = useOnlinePayment();
    const uploadPrescription = useUploadPrescription();

    const [shippingAddress, setShippingAddress] =
        useState("");

    const [paymentMethod, setPaymentMethod] =
        useState("COD");

    const [prescriptionFile, setPrescriptionFile] =
        useState(null);

    const [doctorName, setDoctorName] =
        useState("");

    const [prescriptionId, setPrescriptionId] =
        useState(null);

    /*
     * Cart should not normally be empty when
     * checkout page is opened.
     */
    if (cart.length === 0) {

        return (
            <Box
                sx={{
                    textAlign: "center",
                    py: 10
                }}
            >
                <Typography
                    variant="h6"
                    fontWeight={700}
                >
                    Your cart is empty
                </Typography>

                <Typography
                    color="text.secondary"
                    sx={{
                        mt: 1,
                        mb: 3
                    }}
                >
                    Add medicines before proceeding
                    to checkout.
                </Typography>

                <Button
                    variant="contained"
                    onClick={() =>
                        navigate(
                            "/customer/medicines"
                        )
                    }
                >
                    Browse Medicines
                </Button>
            </Box>
        );
    }

    function handleFileChange(event) {

        const file =
            event.target.files?.[0];

        if (!file) {
            return;
        }

        setPrescriptionFile(file);

        /*
         * If customer changes the file after
         * uploading another prescription,
         * the old prescription must not be
         * accidentally used.
         */
        setPrescriptionId(null);
    }

    function handleUploadPrescription() {

        if (!doctorName.trim()) {

            toast.error(
                "Doctor name is required."
            );

            return;
        }

        if (!prescriptionFile) {

            toast.error(
                "Please select a prescription file."
            );

            return;
        }

        uploadPrescription.mutate(
            {
                file: prescriptionFile,
                doctorName: doctorName.trim()
            },
            {
                onSuccess: response => {

                    const prescription =
                        response.data;

                    setPrescriptionId(
                        prescription.prescriptionId
                    );

                    toast.success(
                        "Prescription uploaded successfully."
                    );
                },

                onError: error => {

                    toast.error(
                        error.response?.data?.message ||
                        "Unable to upload prescription."
                    );
                }
            }
        );
    }

    function handlePlaceOrder() {

    if (!shippingAddress.trim()) {
        toast.error(
            "Shipping address is required."
        );
        return;
    }

    if (
        hasPrescriptionMedicine &&
        !prescriptionId
    ) {
        toast.error(
            "Please upload your prescription before placing the order."
        );
        return;
    }

    const items = cart.map(item => ({
        medicineId: item.medicineId,
        quantity: item.quantity
    }));

    const request = {
        shippingAddress: shippingAddress.trim(),
        paymentMethod,
        items,
        prescriptionId: hasPrescriptionMedicine
            ? prescriptionId
            : null
    };

    checkout.mutate(request, {

        onSuccess: response => {

            const checkoutData = response.data;

            /*
             * Checkout succeeded.
             * Clear cart only after successful
             * order creation.
             */
            clearCart();

            /*
             * Show the EXACT message returned
             * by backend.
             */
            toast.success(
                checkoutData.message ||
                "Order created successfully."
            );

            /*
             * COD:
             *
             * Do NOT call online payment API.
             *
             * Wait 2 seconds and redirect.
             */
            if (
                checkoutData.paymentMethod === "COD"
            ) {

                setTimeout(() => {

                    navigate(
                        "/customer/orders"
                    );

                }, 2000);

                return;
            }

            /*
             * ONLINE:
             *
             * First let the checkout message
             * remain visible for 2 seconds.
             */
            setTimeout(() => {

                onlinePayment.mutate(
                    {
                        orderId:
                            checkoutData.orderId,

                        paymentMethod:
                            checkoutData.paymentMethod
                    },

                    {
                        onSuccess:
                            paymentResponse => {

                                /*
                                 * Show backend payment
                                 * response message.
                                 */
                                toast.success(
                                    paymentResponse?.message ||
                                    "Payment processed successfully."
                                );

                                /*
                                 * Keep payment message
                                 * visible for 2 seconds.
                                 */
                                setTimeout(() => {

                                    navigate(
                                        "/customer/orders"
                                    );

                                }, 2000);
                            },

                        onError: error => {

                            /*
                             * Backend payment failure
                             * message.
                             */
                            toast.error(
                                error.response?.data?.message ||
                                "Payment failed. You can retry the payment from My Orders."
                            );

                            /*
                             * Wait 2 seconds before
                             * redirecting.
                             */
                            setTimeout(() => {

                                navigate(
                                    "/customer/orders"
                                );

                            }, 2000);
                        }
                    }
                );

            }, 2000);
        },

        onError: error => {

            toast.error(
                error.response?.data?.message ||
                "Unable to create order."
            );
        }
    });
}

    const isUploading =
        uploadPrescription.isPending;

    const isProcessing =
        checkout.isPending ||
        onlinePayment.isPending;

    return (
        <Box>

            <Typography
                variant="h4"
                fontWeight={700}
                sx={{ mb: 1 }}
            >
                Checkout
            </Typography>

            <Typography
                color="text.secondary"
                sx={{ mb: 4 }}
            >
                Review your order and complete
                the required information.
            </Typography>

            <Box
                sx={{
                    display: "grid",

                    gridTemplateColumns: {
                        xs: "1fr",
                        lg: "minmax(0, 1fr) 360px"
                    },

                    gap: 3,

                    alignItems: "start"
                }}
            >

                {/* LEFT SIDE */}

                <Stack spacing={3}>

                    {/* SHIPPING ADDRESS */}

                    <Card
                        elevation={0}
                        sx={{
                            border:
                                "1px solid #E5E7EB",
                            borderRadius: "18px"
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

                            <Typography
                                variant="h6"
                                fontWeight={700}
                                sx={{ mb: 2 }}
                            >
                                Shipping Address
                            </Typography>

                            <TextField
                                fullWidth
                                multiline
                                minRows={4}
                                label="Shipping Address"
                                placeholder="Enter complete delivery address"
                                value={
                                    shippingAddress
                                }
                                onChange={event =>
                                    setShippingAddress(
                                        event.target.value
                                    )
                                }
                            />

                        </CardContent>
                    </Card>

                    {/* PAYMENT METHOD */}

                    <Card
                        elevation={0}
                        sx={{
                            border:
                                "1px solid #E5E7EB",
                            borderRadius: "18px"
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

                            <Typography
                                variant="h6"
                                fontWeight={700}
                                sx={{ mb: 2 }}
                            >
                                Payment Method
                            </Typography>

                            <Stack spacing={1}>

                                {[
                                    {
                                        value: "COD",
                                        label:
                                            "Cash on Delivery"
                                    },
                                    {
                                        value: "UPI",
                                        label: "UPI"
                                    },
                                    {
                                        value:
                                            "DEBIT_CARD",
                                        label:
                                            "Debit Card"
                                    },
                                    {
                                        value:
                                            "CREDIT_CARD",
                                        label:
                                            "Credit Card"
                                    }
                                ].map(method => (

                                    <Box
                                        key={
                                            method.value
                                        }
                                        onClick={() =>
                                            setPaymentMethod(
                                                method.value
                                            )
                                        }
                                        sx={{
                                            p: 2,
                                            border:
                                                paymentMethod ===
                                                method.value
                                                    ? "2px solid #2E7D32"
                                                    : "1px solid #E5E7EB",
                                            borderRadius:
                                                "12px",
                                            cursor:
                                                "pointer",
                                            bgcolor:
                                                paymentMethod ===
                                                method.value
                                                    ? "#E8F5E9"
                                                    : "#FFFFFF"
                                        }}
                                    >

                                        <Typography
                                            fontWeight={
                                                600
                                            }
                                        >
                                            {
                                                method.label
                                            }
                                        </Typography>

                                    </Box>

                                ))}

                            </Stack>

                        </CardContent>
                    </Card>

                    {/* PRESCRIPTION */}

                    {hasPrescriptionMedicine && (

                        <Card
                            elevation={0}
                            sx={{
                                border:
                                    "1px solid #E5E7EB",
                                borderRadius: "18px"
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

                                <Typography
                                    variant="h6"
                                    fontWeight={700}
                                    sx={{ mb: 1 }}
                                >
                                    Prescription Required
                                </Typography>

                                <Typography
                                    color="text.secondary"
                                    sx={{ mb: 3 }}
                                >
                                    This order contains
                                    medicine that requires
                                    a prescription. Upload
                                    your prescription from
                                    your computer.
                                </Typography>

                                <Stack spacing={2}>

                                    <TextField
                                        fullWidth
                                        label="Doctor Name"
                                        placeholder="Enter doctor name"
                                        value={
                                            doctorName
                                        }
                                        onChange={event =>
                                            setDoctorName(
                                                event.target
                                                    .value
                                            )
                                        }
                                    />

                                    <Box>

                                        <FormLabel
                                            sx={{
                                                display:
                                                    "block",
                                                mb: 1,
                                                color:
                                                    "#455A64",
                                                fontWeight:
                                                    600
                                            }}
                                        >
                                            Prescription File
                                        </FormLabel>

                                        <Button
                                            variant="outlined"
                                            component="label"
                                        >
                                            {prescriptionFile
                                                ? "Change File"
                                                : "Choose File"}

                                            <input
                                                hidden
                                                type="file"
                                                accept="image/*,.pdf"
                                                onChange={
                                                    handleFileChange
                                                }
                                            />
                                        </Button>

                                    </Box>

                                    {prescriptionFile && (

                                        <Typography
                                            variant="body2"
                                            color="text.secondary"
                                        >
                                            Selected file:
                                            {" "}
                                            {
                                                prescriptionFile.name
                                            }
                                        </Typography>

                                    )}

                                    <Button
                                        variant="contained"
                                        onClick={
                                            handleUploadPrescription
                                        }
                                        disabled={
                                            isUploading ||
                                            !prescriptionFile ||
                                            !doctorName.trim()
                                        }
                                    >
                                        {isUploading ? (
                                            <>
                                                <CircularProgress
                                                    size={20}
                                                    color="inherit"
                                                    sx={{
                                                        mr: 1
                                                    }}
                                                />

                                                Uploading...
                                            </>
                                        ) : (
                                            "Upload Prescription"
                                        )}
                                    </Button>

                                    {prescriptionId && (

                                        <Alert
                                            severity="success"
                                        >
                                            Prescription
                                            uploaded
                                            successfully.

                                            <br />

                                            Prescription ID:
                                            {" "}
                                            {
                                                prescriptionId
                                            }
                                        </Alert>

                                    )}

                                </Stack>

                            </CardContent>
                        </Card>
                    )}

                </Stack>

                {/* RIGHT SIDE - ORDER SUMMARY */}

                <Card
                    elevation={0}
                    sx={{
                        border:
                            "1px solid #E5E7EB",
                        borderRadius: "18px",

                        position: {
                            lg: "sticky"
                        },

                        top: {
                            lg: 100
                        }
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

                        <Typography
                            variant="h6"
                            fontWeight={700}
                            sx={{ mb: 3 }}
                        >
                            Order Summary
                        </Typography>

                        <Stack spacing={2}>

                            {cart.map(item => (

                                <Box
                                    key={
                                        item.medicineId
                                    }
                                    sx={{
                                        display:
                                            "flex",
                                        justifyContent:
                                            "space-between",
                                        gap: 2
                                    }}
                                >

                                    <Box
                                        sx={{
                                            minWidth: 0
                                        }}
                                    >

                                        <Typography
                                            fontWeight={600}
                                        >
                                            {
                                                item.medicineName
                                            }
                                        </Typography>

                                        <Typography
                                            variant="body2"
                                            color="text.secondary"
                                        >
                                            {
                                                item.quantity
                                            }
                                            {" × "}
                                            ₹
                                            {
                                                item.sellingPrice
                                            }
                                        </Typography>

                                    </Box>

                                    <Typography
                                        fontWeight={600}
                                        sx={{
                                            whiteSpace:
                                                "nowrap"
                                        }}
                                    >
                                        ₹
                                        {(
                                            Number(
                                                item.sellingPrice
                                            ) *
                                            item.quantity
                                        ).toFixed(2)}
                                    </Typography>

                                </Box>

                            ))}

                            <Divider />

                            <Box
                                sx={{
                                    display: "flex",
                                    justifyContent:
                                        "space-between"
                                }}
                            >

                                <Typography>
                                    Total Items
                                </Typography>

                                <Typography
                                    fontWeight={600}
                                >
                                    {totalItems}
                                </Typography>

                            </Box>

                            <Box
                                sx={{
                                    display: "flex",
                                    justifyContent:
                                        "space-between",
                                    alignItems:
                                        "center"
                                }}
                            >

                                <Typography
                                    fontWeight={700}
                                >
                                    Total Amount
                                </Typography>

                                <Typography
                                    fontWeight={800}
                                    fontSize={20}
                                    color="#2E7D32"
                                >
                                    ₹
                                    {totalAmount.toFixed(
                                        2
                                    )}
                                </Typography>

                            </Box>

                            {paymentMethod !==
                                "COD" && (

                                <Alert severity="info">
                                    Online payment will
                                    start automatically
                                    after the order is
                                    created.
                                </Alert>

                            )}

                            {hasPrescriptionMedicine &&
                                !prescriptionId && (

                                <Alert severity="warning">
                                    Please upload your
                                    prescription before
                                    placing the order.
                                </Alert>

                            )}

                            <Button
                                fullWidth
                                variant="contained"
                                size="large"
                                onClick={
                                    handlePlaceOrder
                                }
                                disabled={
                                    isProcessing ||
                                    (
                                        hasPrescriptionMedicine &&
                                        !prescriptionId
                                    )
                                }
                                sx={{
                                    mt: 1,
                                    py: 1.4,
                                    fontWeight: 700
                                }}
                            >
                                {checkout.isPending
                                    ? "Creating Order..."
                                    : onlinePayment.isPending
                                    ? "Processing Payment..."
                                    : "Place Order"}
                            </Button>

                        </Stack>

                    </CardContent>
                </Card>

            </Box>
        </Box>
    );
}

export default Checkout;