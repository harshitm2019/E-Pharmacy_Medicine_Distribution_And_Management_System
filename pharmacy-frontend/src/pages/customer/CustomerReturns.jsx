import {
    Box,
    Button,
    Stack,
    Typography
} from "@mui/material";

import { useState } from "react";

import toast from "react-hot-toast";

import EligibleReturnOrders from "../../components/customer/returns/EligibleReturnOrders";

import ReturnRequestDialog from "../../components/customer/returns/ReturnRequestDialog";

import MyReturns from "../../components/customer/returns/MyReturns";

import useMyOrders from "../../hooks/customer/useMyOrders";

import useMyReturns from "../../hooks/customer/useMyReturns";

import useCreateReturn from "../../hooks/customer/useCreateReturn";


function CustomerReturns() {

    const [section, setSection] =
        useState("eligible");


    const [selectedOrder, setSelectedOrder] =
        useState(null);


    const [dialogOpen, setDialogOpen] =
        useState(false);


    /*
     * ==========================================
     * ORDERS
     * ==========================================
     */

    const {
        data: ordersData,
        isLoading: ordersLoading
    } = useMyOrders();


    /*
     * ==========================================
     * RETURNS
     * ==========================================
     */

    const {
        data: returnsData,
        isLoading: returnsLoading,
        refetch: refetchReturns
    } = useMyReturns();


    /*
     * ==========================================
     * CREATE RETURN
     * ==========================================
     */

    const {
        mutateAsync: submitReturn,
        isPending: returnSubmitting
    } = useCreateReturn();


    /*
     * ==========================================
     * ORDERS
     * ==========================================
     */

    const orders =
        ordersData?.data?.content ??
        ordersData?.data ??
        [];


    /*
     * ==========================================
     * ELIGIBLE ORDERS
     * ==========================================
     *
     * Backend already validates eligibility.
     *
     * Frontend only displays DELIVERED orders
     * here.
     * ==========================================
     */

   const returns =
    returnsData?.data?.content ??
    returnsData?.data ??
    [];


const returnedOrderIds = new Set(
    returns.map(
        returnItem => returnItem.orderId
    )
);


const eligibleOrders =
    orders.filter(
        order =>
            order.orderStatus === "DELIVERED" &&
            !returnedOrderIds.has(order.orderId)
    );


    /*
     * ==========================================
     * REQUEST RETURN
     * ==========================================
     */

    function handleRequestReturn(order) {

        setSelectedOrder(order);

        setDialogOpen(true);
    }


    /*
     * ==========================================
     * CLOSE DIALOG
     * ==========================================
     */

    function handleCloseDialog() {

        if (returnSubmitting) {
            return;
        }

        setDialogOpen(false);

        setSelectedOrder(null);
    }


    /*
     * ==========================================
     * SUBMIT RETURN
     * ==========================================
     */

    async function handleSubmitReturn(
        request
    ) {

        try {

            const response =
                await submitReturn(
                    request
                );


            toast.success(
                response?.message ||
                "Return request submitted successfully."
            );


            setDialogOpen(false);

            setSelectedOrder(null);


            /*
             * Refresh My Returns so the
             * newly-created return appears
             * immediately.
             */

            await refetchReturns();

        } catch (error) {

            console.error(
                "Create return error:",
                error
            );


            toast.error(
                error?.response?.data?.message ||
                "Unable to submit return request."
            );
        }
    }


    return (
        <Box>

            {/* ==================================
                HEADER
               ================================== */}

            <Typography
                variant="h4"
                fontWeight={700}
                sx={{ mb: 1 }}
            >
                Returns
            </Typography>


            <Typography
                color="text.secondary"
                sx={{ mb: 4 }}
            >
                Request returns for delivered
                orders and view your return history.
            </Typography>


            {/* ==================================
                SECTION BUTTONS
               ================================== */}

            <Stack
                direction={{
                    xs: "column",
                    sm: "row"
                }}
                spacing={2}
                sx={{ mb: 4 }}
            >

                <Button
                    variant={
                        section === "eligible"
                            ? "contained"
                            : "outlined"
                    }
                    onClick={() =>
                        setSection(
                            "eligible"
                        )
                    }
                >
                    Eligible for Return
                </Button>


                <Button
                    variant={
                        section === "returns"
                            ? "contained"
                            : "outlined"
                    }
                    onClick={() =>
                        setSection(
                            "returns"
                        )
                    }
                >
                    My Returns
                </Button>

            </Stack>


            {/* ==================================
                ELIGIBLE ORDERS
               ================================== */}

            {section === "eligible" && (

                <EligibleReturnOrders
                    orders={
                        eligibleOrders
                    }
                    isLoading={
                        ordersLoading
                    }
                    onRequestReturn={
                        handleRequestReturn
                    }
                />

            )}


            {/* ==================================
                MY RETURNS
               ================================== */}

            {section === "returns" && (

                <MyReturns
                    data={returnsData}
                    isLoading={
                        returnsLoading
                    }
                />

            )}


            {/* ==================================
                RETURN REQUEST DIALOG
               ================================== */}

            <ReturnRequestDialog
                open={dialogOpen}
                order={selectedOrder}
                onClose={
                    handleCloseDialog
                }
                onSubmit={
                    handleSubmitReturn
                }
                loading={
                    returnSubmitting
                }
            />

        </Box>
    );
}
export default CustomerReturns;