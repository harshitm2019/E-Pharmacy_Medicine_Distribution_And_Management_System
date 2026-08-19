import CloseIcon from "@mui/icons-material/Close";
import { Box, Button, Checkbox, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle, FormControlLabel, IconButton, Typography } from "@mui/material";
import { useEffect, useState } from "react";

function DeliveryStatusDialog({ open, delivery, order, orderLoading, loading, onClose, onConfirm }) {
    const [cashCollected, setCashCollected] = useState(false);

    useEffect(() => {
        if (!open) setCashCollected(false);
    }, [open]);

    if (!delivery) return null;

    const currentStatus = delivery.deliveryStatus;
    const nextStatus = currentStatus === "ASSIGNED" ? "OUT_FOR_DELIVERY" : currentStatus === "OUT_FOR_DELIVERY" ? "DELIVERED" : null;
    const isDelivering = nextStatus === "DELIVERED";

    // PAID -> online payment completed | PENDING -> COD payment pending
    const isCod = order?.paymentStatus === "PENDING";
    const canConfirm = !loading && !orderLoading && !!order && !!nextStatus && (!isDelivering || !isCod || cashCollected);

    function handleConfirm() {
        if (!canConfirm) return;
        onConfirm(delivery.orderId, nextStatus, isDelivering ? cashCollected : false);
    }

    function handleClose() {
        setCashCollected(false);
        onClose();
    }

    return (
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
            <DialogTitle>
                Update Delivery Status
                <IconButton onClick={handleClose} sx={{ position: "absolute", right: 8, top: 8 }}><CloseIcon /></IconButton>
            </DialogTitle>

            <DialogContent>
                <Box sx={{ py: 2 }}>
                    <Typography fontWeight={700} sx={{ mb: 1 }}>Order #{delivery.orderId}</Typography>
                    <Typography color="text.secondary">Current Status: {currentStatus}</Typography>
                    <Typography sx={{ mt: 1 }}>New Status: <strong>{nextStatus}</strong></Typography>

                    {/* Payment info */}
                    {orderLoading ? (
                        <Box sx={{ display: "flex", alignItems: "center", gap: 1, mt: 3 }}>
                            <CircularProgress size={20} />
                            <Typography color="text.secondary">Loading payment details...</Typography>
                        </Box>
                    ) : order ? (
                        <Box sx={{ mt: 3, p: 2, borderRadius: 2, bgcolor: isCod ? "#FFF8E1" : "#E8F5E9", border: "1px solid", borderColor: isCod ? "#FFE082" : "#A5D6A7" }}>
                            <Typography fontWeight={700} sx={{ color: isCod ? "#E65100" : "#2E7D32" }}>Payment Status: {order.paymentStatus}</Typography>
                            {isCod && <Typography variant="body2" sx={{ mt: 0.5, color: "#795548" }}>COD Order</Typography>}
                        </Box>
                    ) : null}

                    {/* Cash collection */}
                    {isDelivering && isCod && (
                        <Box sx={{ mt: 2, p: 2, borderRadius: 2, bgcolor: "#FFF8E1", border: "1px solid #FFE082" }}>
                            <FormControlLabel
                                control={<Checkbox checked={cashCollected} onChange={(e) => setCashCollected(e.target.checked)} />}
                                label="Cash collected from customer"
                            />
                            <Typography variant="body2" color="text.secondary">Confirm that you have collected cash before marking this order as delivered.</Typography>
                        </Box>
                    )}
                </Box>
            </DialogContent>

            <DialogActions sx={{ p: 2, gap: 1 }}>
                <Button onClick={handleClose} disabled={loading}>Cancel</Button>
                <Button variant="contained" onClick={handleConfirm} disabled={!canConfirm}>
                    {loading ? "Updating..." : `Confirm ${nextStatus}`}
                </Button>
            </DialogActions>
        </Dialog>
    );
}
export default DeliveryStatusDialog;