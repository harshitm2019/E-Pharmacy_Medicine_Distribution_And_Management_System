import CloseIcon from "@mui/icons-material/Close";
import { Dialog, DialogContent, DialogTitle, Divider, IconButton, Stack, Typography } from "@mui/material";

function DeliveryStatusDialog({ open, delivery, onClose }) {
    if (!delivery) return null;

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
            <DialogTitle>
                Delivery Details
                <IconButton onClick={onClose} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>
                <Stack spacing={2}>
                    <Typography><strong>Order ID:</strong> {delivery.orderId}</Typography>

                    <Divider />

                    <Typography><strong>Delivery Status:</strong> {delivery.deliveryStatus}</Typography>
                    <Typography><strong>Delivery Boy:</strong> {delivery.deliveryBoyName}</Typography>
                    <Typography><strong>Vehicle Number:</strong> {delivery.vehicleNo}</Typography>
                    <Typography><strong>Assigned Date:</strong> {delivery.assignedDate || "N/A"}</Typography>
                    <Typography><strong>Expected Delivery:</strong> {delivery.expectedDeliveryDate || "N/A"}</Typography>
                </Stack>
            </DialogContent>
        </Dialog>
    );
}
export default DeliveryStatusDialog;