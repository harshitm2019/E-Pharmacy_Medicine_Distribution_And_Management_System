import {
    Box,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    Typography
} from "@mui/material";
import { useState } from "react";

import DeliveryOrderTable from "../../components/delivery/DeliveryOrderTable";

import DeliveryOrderDetails from "../../components/delivery/deliveryOrderDetails";

import DeliveryStatusDialog from "../../components/delivery/DeliveryStatusDialog";

import useMyDeliveryOrders from "../../hooks/delivery/useMyDeliveryOrders";

import useDeliveryOrderDetails from "../../hooks/delivery/useDeliveryOrderDetails";

import useUpdateDeliveryStatus from "../../hooks/delivery/useUpdateDeliveryStatus";


function Deliveries() {

    const [status, setStatus] =
        useState("ASSIGNED");

    const [selectedOrderId, setSelectedOrderId] =
        useState(null);

    const [statusOrder, setStatusOrder] =
        useState(null);


    const {
        data,
        isLoading
    } = useMyDeliveryOrders(status);


    const {
        data: orderData,
        isLoading: orderLoading
    } = useDeliveryOrderDetails(
        selectedOrderId
    );


    const updateMutation =
        useUpdateDeliveryStatus();


    const deliveries =
        data?.data?.content ?? [];

    const selectedOrder =
        orderData?.data;


    function handleViewDetails(orderId) {

        setSelectedOrderId(orderId);
    }


    function handleUpdateStatus(delivery) {

        /*
         * First select the order so the order-details
         * query fetches its payment status.
         */
        setSelectedOrderId(
            delivery.orderId
        );

        setStatusOrder(
            delivery
        );
    }


    function handleConfirmStatus(
        orderId,
        newStatus,
        cashCollected
    ) {

        updateMutation.mutate(
            {
                orderId,
                status: newStatus,
                cashCollected
            },
            {
                onSuccess: () => {

                    setStatusOrder(null);
                    setSelectedOrderId(null);

                }
            }
        );
    }


    function handleCloseDetails() {

        setSelectedOrderId(null);
    }


    function handleCloseStatus() {

        setStatusOrder(null);
        setSelectedOrderId(null);
    }


    return (
        <Box>

            <Typography
                variant="h4"
                fontWeight={700}
                sx={{ mb: 3 }}
            >
                Deliveries
            </Typography>


            <FormControl
                size="small"
                sx={{
                    minWidth: 220,
                    mb: 3
                }}
            >

                <InputLabel>
                    Delivery Status
                </InputLabel>

                <Select
                    value={status}
                    label="Delivery Status"
                    onChange={(event) =>
                        setStatus(
                            event.target.value
                        )
                    }
                >

                    <MenuItem value="ASSIGNED">
                        Assigned
                    </MenuItem>

                    <MenuItem value="OUT_FOR_DELIVERY">
                        Out for Delivery
                    </MenuItem>

                    <MenuItem value="DELIVERED">
                        Delivered
                    </MenuItem>

                </Select>

            </FormControl>


            <DeliveryOrderTable
                deliveries={deliveries}
                loading={isLoading}
                onViewDetails={
                    handleViewDetails
                }
                onUpdateStatus={
                    handleUpdateStatus
                }
            />


            <DeliveryOrderDetails
                open={
                    !!selectedOrderId &&
                    !statusOrder
                }
                order={selectedOrder}
                loading={orderLoading}
                onClose={
                    handleCloseDetails
                }
            />


            <DeliveryStatusDialog
                open={
                    !!statusOrder
                }
                delivery={statusOrder}
                order={selectedOrder}
                orderLoading={orderLoading}
                loading={
                    updateMutation.isPending
                }
                onClose={
                    handleCloseStatus
                }
                onConfirm={
                    handleConfirmStatus
                }
            />

        </Box>
    );
}


export default Deliveries;