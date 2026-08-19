import { Box, FormControl, InputLabel, MenuItem, Select, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import OrderDetailsDialog from "../../components/admin/order/OrderDetailsDialog";
import ReturnTable from "../../components/admin/return/ReturnTable";
import UpdateReturnStatusDialog from "../../components/admin/return/UpdateReturnStatusDialog";
import useOrderById from "../../hooks/admin/useOrderById";
import useReturns from "../../hooks/admin/useReturns";
import useUpdateReturnStatus from "../../hooks/admin/useUpdateReturnStatus";

function Returns() {
    const [status, setStatus] = useState("PENDING");
    const [selectedOrderId, setSelectedOrderId] = useState(null);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [selectedReturn, setSelectedReturn] = useState(null);
    const [selectedOrder, setSelectedOrder] = useState(null);
    const [openOrderDetails, setOpenOrderDetails] = useState(false);

    const { data, isLoading } = useReturns({ status, page, size });
    const updateReturnStatus = useUpdateReturnStatus();
    const { data: orderData } = useOrderById(selectedOrderId);

    function handleStatusChange(event) {
        setStatus(event.target.value);
        setPage(0);
    }

    function handleUpdate(returnOrder) {
        setSelectedReturn(returnOrder);
    }

    function handleCloseDialog() {
        if (updateReturnStatus.isPending) return;
        setSelectedReturn(null);
    }

    function handleViewOrder(orderId) {
        setSelectedOrderId(orderId);
    }
    function handleConfirm(newStatus) {
        updateReturnStatus.mutate(
            {
                returnId: selectedReturn.returnId,
                returnStatus: newStatus
            },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setSelectedReturn(null);
                },
                onError: error => {
                    toast.error(error.response?.data?.message || "Failed to update return status.");
                }
            }
        );
    }

    return (
        <Box>
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: { xs: "flex-start", sm: "center" },
                    flexDirection: { xs: "column", sm: "row" },
                    gap: 2,
                    mb: 3
                }}
            >
                <Typography variant="h4" fontWeight={700}>
                    Returns
                </Typography>

                <FormControl size="small" sx={{ minWidth: 200 }}>
                    <InputLabel>Status</InputLabel>

                    <Select
                        value={status}
                        label="Status"
                        onChange={handleStatusChange}
                    >
                        <MenuItem value="PENDING">Pending</MenuItem>
                        <MenuItem value="APPROVED">Approved</MenuItem>
                        <MenuItem value="REJECTED">Rejected</MenuItem>
                        <MenuItem value="REFUNDED">Refunded</MenuItem>
                    </Select>
                </FormControl>
            </Box>

            <ReturnTable
                data={data}
                isLoading={isLoading}
                page={page}
                size={size}
                setPage={setPage}
                setSize={setSize}
                onUpdate={handleUpdate}
                onViewOrder={handleViewOrder}
            />

            <OrderDetailsDialog
                open={!!selectedOrderId}
                order={orderData?.data}
                onClose={() => setSelectedOrderId(null)}
            />

            <UpdateReturnStatusDialog
                open={!!selectedReturn}
                returnOrder={selectedReturn}
                onClose={handleCloseDialog}
                onConfirm={handleConfirm}
                loading={updateReturnStatus.isPending}
            />
        </Box>
    );
}
export default Returns;