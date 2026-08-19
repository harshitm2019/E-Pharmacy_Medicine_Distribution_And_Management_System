import { Box, FormControl, InputLabel, MenuItem, Select, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import AssignDeliveryBoyDialog from "../../components/admin/delivery/AssignDeliveryBoyDialog";
import CancelOrderDialog from "../../components/admin/order/CancelOrderDialog";
import OrderDetailsDialog from "../../components/admin/order/OrderDetailsDialog";
import OrderStatusDialog from "../../components/admin/order/OrderStatusDialog";
import OrderTable from "../../components/admin/order/OrderTable";
import PrescriptionDecisionDialog from "../../components/admin/order/PrescriptionDecisionDialog";
import PrescriptionDetailsDialog from "../../components/admin/order/PrescriptionDetailsDialog";
import useCancelOrder from "../../hooks/admin/useCancelOrder";
import useOrders from "../../hooks/admin/useOrders";
import useUpdateOrderStatus from "../../hooks/admin/useUpdateOrderStatus";
import useUpdatePrescriptionStatus from "../../hooks/admin/useUpdatePrescriptionStatus";

function Orders() {
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [status, setStatus] = useState("PENDING");
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [openDetails, setOpenDetails] = useState(false);
    const [cancelOrder, setCancelOrder] = useState(null);
    const [prescriptionDecision, setPrescriptionDecision] = useState(null);
    const [orderStatusDecision, setOrderStatusDecision] = useState(null);
    const [selectedPrescription, setSelectedPrescription] = useState(null);
    const [assignDialogOpen, setAssignDialogOpen] = useState(false);
    const [selectedOrderId, setSelectedOrderId] = useState(null);

    const { data, isLoading } = useOrders({ page, size, status });
    const updateOrderStatus = useUpdateOrderStatus();
    const updatePrescriptionStatus = useUpdatePrescriptionStatus();
    const cancelOrderMutation = useCancelOrder();

    const orders = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    function handleStatusChange(event) {
        setStatus(event.target.value);
        setPage(0);
    }

    function handleView(order) {
        setSelectedOrder(order);
        setOpenDetails(true);
    }

    function handleCloseDetails() {
        setSelectedOrder(null);
        setOpenDetails(false);
    }

    function handlePrescriptionView(prescription) {
        if (prescription) {
            setSelectedPrescription(prescription);
        }
    }

    function handleOrderStatus(order, orderStatus) {
        setOrderStatusDecision({ order, orderStatus });
    }

    function confirmOrderStatus() {
        updateOrderStatus.mutate(
            {
                orderId: orderStatusDecision.order.orderId,
                orderStatus: orderStatusDecision.orderStatus
            },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setOrderStatusDecision(null);
                },
                onError: error => toast.error(error.response?.data?.message || "Failed to update order status.")
            }
        );
    }

    function handlePrescriptionStatus(order, prescriptionStatus) {
        setPrescriptionDecision({ order, prescriptionStatus });
    }

    function confirmPrescriptionDecision() {
        updatePrescriptionStatus.mutate(
            {
                orderId: prescriptionDecision.order.orderId,
                prescriptionStatus: prescriptionDecision.prescriptionStatus
            },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setPrescriptionDecision(null);
                },
                onError: error => toast.error(error.response?.data?.message || "Failed to update prescription status.")
            }
        );
    }

    function handleCancel(order) {
        setCancelOrder(order);
    }

    function confirmCancel() {
        cancelOrderMutation.mutate(cancelOrder.orderId, {
            onSuccess: response => {
                toast.success(response.message);
                setCancelOrder(null);
            },
            onError: error => toast.error(error.response?.data?.message || "Failed to cancel order.")
        });
    }

    return (
        <Box>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 3 }}>
                <Typography variant="h4" fontWeight={700}>Orders</Typography>

                <FormControl size="small" sx={{ minWidth: 200 }}>
                    <InputLabel>Status</InputLabel>
                    <Select value={status} label="Status" onChange={handleStatusChange}>
                        <MenuItem value="PENDING">Pending</MenuItem>
                        <MenuItem value="CONFIRMED">Confirmed</MenuItem>
                        <MenuItem value="PACKED">Packed</MenuItem>
                        <MenuItem value="OUT_FOR_DELIVERY">Out for Delivery</MenuItem>
                        <MenuItem value="DELIVERED">Delivered</MenuItem>
                        <MenuItem value="CANCELLED">Cancelled</MenuItem>
                    </Select>
                </FormControl>
            </Box>

            <OrderTable
                orders={orders}
                isLoading={isLoading}
                page={page}
                size={size}
                pageData={pageData}
                setPage={setPage}
                setSize={setSize}
                onView={handleView}
                onOrderStatus={handleOrderStatus}
                onPrescriptionStatus={handlePrescriptionStatus}
                onCancel={handleCancel}
                onPrescriptionView={handlePrescriptionView}
            />

            <OrderDetailsDialog
                open={openDetails}
                order={selectedOrder}
                onClose={handleCloseDetails}
                onAssign={orderId => {
                    setSelectedOrderId(orderId);
                    setAssignDialogOpen(true);
                }}
            />
            <AssignDeliveryBoyDialog
                open={assignDialogOpen}
                orderId={selectedOrderId}
                onClose={() => {
                    setAssignDialogOpen(false);
                    setSelectedOrderId(null);
                }}
            />

            <PrescriptionDetailsDialog
                open={!!selectedPrescription}
                prescription={selectedPrescription}
                onClose={() => setSelectedPrescription(null)}
            />

            <CancelOrderDialog
                open={!!cancelOrder}
                order={cancelOrder}
                onClose={() => setCancelOrder(null)}
                onConfirm={confirmCancel}
                loading={cancelOrderMutation.isPending}
            />

            <PrescriptionDecisionDialog
                open={!!prescriptionDecision}
                order={prescriptionDecision?.order}
                status={prescriptionDecision?.prescriptionStatus}
                onClose={() => setPrescriptionDecision(null)}
                onConfirm={confirmPrescriptionDecision}
                loading={updatePrescriptionStatus.isPending}
            />

            <OrderStatusDialog
                open={!!orderStatusDecision}
                order={orderStatusDecision?.order}
                status={orderStatusDecision?.orderStatus}
                onClose={() => setOrderStatusDecision(null)}
                onConfirm={confirmOrderStatus}
                loading={updateOrderStatus.isPending}
            />
        </Box>
    );
}
export default Orders;