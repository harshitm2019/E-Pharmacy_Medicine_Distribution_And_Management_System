import {
    Box,
    Button,
    CircularProgress,
    Typography
} from "@mui/material";

import { useState } from "react";

import toast from "react-hot-toast";

import OrderCard
    from "../../components/customer/order/OrderCard";

import CustomerOrderDetailsDialog
    from "../../components/customer/order/CustomerOrderDetailsDialog";

import PaymentReceipt
    from "../../components/customer/order/PaymentReceipt";

import DeliveryStatusDialog
    from "../../components/customer/order/DeliveryStatusDialog";

import useMyOrders
    from "../../hooks/customer/useMyOrders";

import useMyPayments
    from "../../hooks/customer/useMyPayments";

import useDeliveryStatus
    from "../../hooks/customer/useDeliveryStatus";

import {
    processOnlinePayment
} from "../../services/paymentService";

import {
    cancelOrder,
    updateOrder
} from "../../services/orderService";


function MyOrders() {

    /*
     * ==========================================
     * ORDERS
     * ==========================================
     */

    const {
        data: ordersData,
        isLoading: ordersLoading,
        isError: ordersError,
        refetch: refetchOrders
    } = useMyOrders();


    /*
     * ==========================================
     * PAYMENTS
     * ==========================================
     */

    const {
        data: paymentsData,
        isLoading: paymentsLoading
    } = useMyPayments();


    /*
     * ==========================================
     * STATE
     * ==========================================
     */

    const [selectedOrder, setSelectedOrder] =
        useState(null);

    const [detailsOpen, setDetailsOpen] =
        useState(false);

    const [receiptOpen, setReceiptOpen] =
        useState(false);

    const [trackingOrder, setTrackingOrder] =
        useState(null);

    const [trackingOpen, setTrackingOpen] =
        useState(false);

    const [
        isChangingPaymentMethod,
        setIsChangingPaymentMethod
    ] = useState(false);


    /*
     * ==========================================
     * ORDERS DATA
     * ==========================================
     */

    const orders =
        ordersData?.data?.content ??
        ordersData?.data ??
        [];


    /*
     * ==========================================
     * PAYMENTS DATA
     * ==========================================
     */

    const payments =
        paymentsData?.data ?? [];


    /*
     * ==========================================
     * FIND PAYMENT FOR ORDER
     * ==========================================
     */

    function getPaymentForOrder(orderId) {

        return payments.find(
            payment =>
                payment.orderId === orderId
        );
    }


    /*
     * ==========================================
     * SELECTED PAYMENT
     * ==========================================
     */

    const selectedPayment =
        selectedOrder
            ? getPaymentForOrder(
                selectedOrder.orderId
            )
            : null;


    /*
     * ==========================================
     * DELIVERY DATA
     *
     * This is used only for the currently
     * tracked order.
     * ==========================================
     */

    const {
        data: deliveryData,
        isLoading: deliveryLoading
    } = useDeliveryStatus(
        trackingOrder?.orderId,
        trackingOpen
    );


    const delivery =
        deliveryData?.data ?? null;


    /*
     * ==========================================
     * VIEW ORDER DETAILS
     * ==========================================
     */

    function handleView(order) {

        setSelectedOrder(order);

        setDetailsOpen(true);
    }


    /*
     * ==========================================
     * VIEW PAYMENT RECEIPT
     * ==========================================
     */

    function handlePaymentReceipt(order) {

        setSelectedOrder(order);

        setReceiptOpen(true);
    }


    /*
     * ==========================================
     * TRACK DELIVERY
     *
     * IMPORTANT:
     * This opens DeliveryStatusDialog.
     * It does NOT open Order Details.
     * ==========================================
     */

    function handleTrack(order) {

        setTrackingOrder(order);

        setTrackingOpen(true);
    }


    /*
     * ==========================================
     * CLOSE ORDER DETAILS
     * ==========================================
     */

    function handleCloseDetails() {

        setDetailsOpen(false);

        setSelectedOrder(null);
    }


    /*
     * ==========================================
     * CLOSE RECEIPT
     * ==========================================
     */

    function handleCloseReceipt() {

        setReceiptOpen(false);

        setSelectedOrder(null);
    }


    /*
     * ==========================================
     * CLOSE DELIVERY TRACKING
     * ==========================================
     */

    function handleCloseTracking() {

        setTrackingOpen(false);

        setTrackingOrder(null);
    }


    /*
     * ==========================================
     * ONLINE PAYMENT
     * ==========================================
     *
     * COD never reaches the online payment API.
     * ==========================================
     */

    async function handlePayment(
        order,
        payment
    ) {

        if (!payment) {

            toast.error(
                "Payment information not found."
            );

            return;
        }


        if (
            payment.paymentMethod ===
            "COD"
        ) {

            return;
        }


        try {

            const response =
                await processOnlinePayment(
                    order.orderId,
                    payment.paymentMethod
                );


            toast.success(
                response?.message ||
                "Payment processed successfully."
            );


            await refetchOrders();

        } catch (error) {

            console.error(
                "Payment error:",
                error
            );


            toast.error(
                error?.response?.data?.message ||
                "Payment failed."
            );
        }
    }


    /*
     * ==========================================
     * CANCEL ORDER
     * ==========================================
     */

    async function handleCancel(orderId) {

        try {

            await cancelOrder(orderId);


            toast.success(
                "Order cancelled successfully."
            );


            await refetchOrders();


            if (
                selectedOrder?.orderId ===
                orderId
            ) {

                setDetailsOpen(false);

                setReceiptOpen(false);

                setSelectedOrder(null);
            }


            if (
                trackingOrder?.orderId ===
                orderId
            ) {

                setTrackingOpen(false);

                setTrackingOrder(null);
            }

        } catch (error) {

            console.error(
                "Cancel order error:",
                error
            );


            toast.error(
                error?.response?.data?.message ||
                "Unable to cancel order."
            );
        }
    }


    /*
     * ==========================================
     * UPDATE PAYMENT METHOD
     * ==========================================
     */

    async function handleChangePaymentMethod(
        orderId,
        paymentMethod
    ) {

        if (!paymentMethod) {

            toast.error(
                "Please select a payment method."
            );

            return;
        }


        try {

            setIsChangingPaymentMethod(
                true
            );


            const response =
                await updateOrder(
                    orderId,
                    {
                        paymentMethod
                    },
                    null
                );


            toast.success(
                response?.message ||
                "Payment method updated successfully."
            );


            await refetchOrders();


            setDetailsOpen(false);

            setSelectedOrder(null);

        } catch (error) {

            console.error(
                "Update payment method error:",
                error
            );


            toast.error(
                error?.response?.data?.message ||
                "Unable to update payment method."
            );

        } finally {

            setIsChangingPaymentMethod(
                false
            );
        }
    }


    /*
     * ==========================================
     * LOADING
     * ==========================================
     */

    if (
        ordersLoading ||
        paymentsLoading
    ) {

        return (
            <Box
                sx={{
                    display: "flex",
                    justifyContent:
                        "center",
                    alignItems:
                        "center",
                    py: 10
                }}
            >
                <CircularProgress />
            </Box>
        );
    }


    /*
     * ==========================================
     * ERROR
     * ==========================================
     */

    if (ordersError) {

        return (
            <Box
                sx={{
                    textAlign: "center",
                    py: 8
                }}
            >

                <Typography
                    color="error"
                    sx={{ mb: 2 }}
                >
                    Unable to load your orders.
                </Typography>


                <Button
                    variant="contained"
                    onClick={refetchOrders}
                >
                    Try Again
                </Button>

            </Box>
        );
    }


    return (
        <Box>

            {/* ==================================
                PAGE HEADER
               ================================== */}

            <Typography
                variant="h4"
                fontWeight={700}
                sx={{ mb: 1 }}
            >
                My Orders
            </Typography>


            <Typography
                color="text.secondary"
                sx={{ mb: 4 }}
            >
                View your orders, payment status
                and delivery information.
            </Typography>


            {/* ==================================
                NO ORDERS
               ================================== */}

            {orders.length === 0 ? (

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
                        No orders found
                    </Typography>


                    <Typography
                        color="text.secondary"
                        sx={{ mt: 1 }}
                    >
                        Your orders will appear here
                        after checkout.
                    </Typography>

                </Box>

            ) : (

                /*
                 * TWO CARDS PER ROW
                 */

                <Box
                    sx={{
                        display: "grid",

                        gridTemplateColumns: {
                            xs: "1fr",
                            sm: "repeat(2, minmax(0, 1fr))"
                        },

                        gap: 3,

                        alignItems: "stretch"
                    }}
                >

                    {orders.map(order => {

                        const payment =
                            getPaymentForOrder(
                                order.orderId
                            );


                        return (
                            <OrderCard
                                key={
                                    order.orderId
                                }

                                order={order}

                                payment={payment}

                    
                                onView={
                                    handleView
                                }

                                onPayment={
                                    handlePayment
                                }

                                onPaymentReceipt={
                                    handlePaymentReceipt
                                }

                                onTrack={
                                    handleTrack
                                }

                                onCancel={
                                    handleCancel
                                }
                            />
                        );
                    })}

                </Box>
            )}


            {/* ==================================
                ORDER DETAILS
               ================================== */}

            <CustomerOrderDetailsDialog
                open={detailsOpen}

                order={selectedOrder}

                payment={selectedPayment}

                deliveryStatus={null}

                onClose={
                    handleCloseDetails
                }

                onPayment={
                    handlePayment
                }

                onChangePaymentMethod={
                    handleChangePaymentMethod
                }

                isChangingPaymentMethod={
                    isChangingPaymentMethod
                }
            />


            {/* ==================================
                PAYMENT RECEIPT
               ================================== */}

            <PaymentReceipt
                open={receiptOpen}

                order={selectedOrder}

                payment={selectedPayment}

                onClose={
                    handleCloseReceipt
                }
            />


            {/* ==================================
                DELIVERY STATUS
               ================================== */}

            <DeliveryStatusDialog
                open={trackingOpen}

                orderId={
                    trackingOrder?.orderId
                }

                delivery={delivery}

                loading={deliveryLoading}

                onClose={
                    handleCloseTracking
                }
            />

        </Box>
    );
}


export default MyOrders;