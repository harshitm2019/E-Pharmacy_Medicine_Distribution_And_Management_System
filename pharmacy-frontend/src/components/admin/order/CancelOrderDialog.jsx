import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material";

function CancelOrderDialog({ open, order, onClose, onConfirm, loading }) {
    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>Cancel Order</DialogTitle>

            <DialogContent>
                <Typography>
                    Are you sure you want to cancel order <strong>#{order?.orderId}</strong>?
                </Typography>
            </DialogContent>

            <DialogActions sx={{ px: 3, pb: 3 }}>
                <Button onClick={onClose} disabled={loading}>No</Button>
                <Button onClick={onConfirm} color="error" variant="contained" disabled={loading}>
                    {loading ? "Cancelling..." : "Yes, Cancel"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default CancelOrderDialog;