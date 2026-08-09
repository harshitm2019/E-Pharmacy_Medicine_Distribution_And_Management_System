import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material";

function OrderStatusDialog({ open, order, status, onClose, onConfirm, loading }) {
    const packed = status === "PACKED";

    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>{packed ? "Pack Order" : "Confirm Order"}</DialogTitle>

            <DialogContent>
                <Typography>
                    Are you sure you want to {packed ? "mark" : "confirm"} order <strong>#{order?.orderId}</strong> as {status}?
                </Typography>
            </DialogContent>

            <DialogActions sx={{ px: 3, pb: 3 }}>
                <Button onClick={onClose} disabled={loading}>Cancel</Button>
                <Button onClick={onConfirm} variant="contained" disabled={loading}>
                    {loading ? "Updating..." : packed ? "Mark Packed" : "Confirm"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default OrderStatusDialog;