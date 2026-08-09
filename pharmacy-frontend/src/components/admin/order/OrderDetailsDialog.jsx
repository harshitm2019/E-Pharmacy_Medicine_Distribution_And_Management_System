import CloseIcon from "@mui/icons-material/Close";
import { Box, Dialog, DialogContent, DialogTitle, Divider, IconButton, Stack, Typography } from "@mui/material";

function OrderDetailsDialog({ open, order, onClose }) {
    if (!order) return null;

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
            <DialogTitle>
                Order #{order.orderId}
                <IconButton onClick={onClose} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>
                <Stack spacing={2}>
                    <Box>
                        <Typography><strong>Order Date:</strong> {order.orderDate}</Typography>
                        <Typography><strong>Order Status:</strong> {order.orderStatus}</Typography>
                        <Typography><strong>Payment Status:</strong> {order.paymentStatus}</Typography>
                        <Typography><strong>Shipping Address:</strong> {order.shippingAddress}</Typography>
                    </Box>

                    <Divider />

                    <Typography variant="h6">Order Items</Typography>

                    {order.items?.map(item => (
                        <Box key={item.medicineId} sx={{ p: 2, border: "1px solid #E5E7EB", borderRadius: 2 }}>
                            <Typography fontWeight={600}>{item.medicineName}</Typography>
                            <Typography>Quantity: {item.quantity}</Typography>
                            <Typography>Subtotal: ₹{item.subTotal}</Typography>
                            <Typography>Discount: {item.discount}</Typography>
                            <Typography>Tax: ₹{item.tax}</Typography>
                        </Box>
                    ))}

                    <Divider />

                    <Typography variant="h6">
                        Total Amount: ₹{order.totalAmount}
                    </Typography>

                    {order.prescription && (
                        <>
                            <Divider />

                            <Typography variant="h6">Prescription</Typography>

                            <Typography>
                                <strong>Prescription ID:</strong> {order.prescription.prescriptionId}
                            </Typography>

                            <Typography>
                                <strong>Doctor:</strong> {order.prescription.doctorName}
                            </Typography>

                            <Typography>
                                <strong>Status:</strong> {order.prescription.status}
                            </Typography>

                            <Typography>
                                <strong>Uploaded:</strong> {order.prescription.uploadedDate}
                            </Typography>

                            {order.prescription.prescriptionUrl && (
                                <Typography>
                                    <a href={order.prescription.prescriptionUrl} target="_blank" rel="noreferrer">
                                        View Prescription
                                    </a>
                                </Typography>
                            )}
                        </>
                    )}
                </Stack>
            </DialogContent>
        </Dialog>
    );
}

export default OrderDetailsDialog;