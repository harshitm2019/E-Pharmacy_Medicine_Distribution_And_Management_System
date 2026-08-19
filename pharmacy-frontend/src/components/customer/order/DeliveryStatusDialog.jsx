import CloseIcon from "@mui/icons-material/Close";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import {
    Box,
    Dialog,
    DialogContent,
    DialogTitle,
    Divider,
    IconButton,
    Stack,
    Typography
} from "@mui/material";

function DeliveryStatusDialog({
    open,
    orderId,
    delivery,
    loading,
    onClose
}) {
    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="sm"
        >
            <DialogTitle>
                Delivery Details

                <IconButton
                    onClick={onClose}
                    sx={{
                        position: "absolute",
                        right: 8,
                        top: 8
                    }}
                >
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>
                {loading ? (
                    <Box
                        sx={{
                            textAlign: "center",
                            py: 6
                        }}
                    >
                        <Typography color="text.secondary">
                            Loading delivery details...
                        </Typography>
                    </Box>
                ) : !delivery ? (
                    <Box
                        sx={{
                            textAlign: "center",
                            py: 6
                        }}
                    >
                        <LocalShippingOutlinedIcon
                            sx={{
                                fontSize: 60,
                                color: "#90A4AE",
                                mb: 2
                            }}
                        />

                        <Typography
                            variant="h6"
                            fontWeight={700}
                        >
                            Delivery Not Assigned Yet
                        </Typography>

                        <Typography
                            color="text.secondary"
                            sx={{ mt: 1 }}
                        >
                            Order #{orderId} has not been assigned
                            to a delivery boy yet.
                        </Typography>
                    </Box>
                ) : (
                    <Stack spacing={2.5}>

                        <Box
                            sx={{
                                textAlign: "center",
                                py: 2
                            }}
                        >
                            <LocalShippingOutlinedIcon
                                sx={{
                                    fontSize: 60,
                                    color: "#2E7D32"
                                }}
                            />

                            <Typography
                                variant="h6"
                                fontWeight={700}
                                sx={{ mt: 1 }}
                            >
                                Order #{orderId}
                            </Typography>
                        </Box>

                        <Divider />

                        <Box>
                            <Typography
                                color="text.secondary"
                                sx={{ mb: 0.5 }}
                            >
                                Delivery Status
                            </Typography>

                            <Typography
                                sx={{
                                    fontSize: 22,
                                    fontWeight: 700,
                                    color: "#2E7D32"
                                }}
                            >
                                {delivery.deliveryStatus}
                            </Typography>
                        </Box>

                        <Box>
                            <Typography color="text.secondary">
                                Expected Delivery Date
                            </Typography>

                            <Typography fontWeight={600}>
                                {delivery.expectedDeliveryDate
                                    ? new Date(
                                          delivery.expectedDeliveryDate
                                      ).toLocaleDateString()
                                    : "Not available"}
                            </Typography>
                        </Box>

                        <Box>
                            <Typography color="text.secondary">
                                Assigned Date
                            </Typography>

                            <Typography fontWeight={600}>
                                {delivery.assignedDate
                                    ? new Date(
                                          delivery.assignedDate
                                      ).toLocaleString()
                                    : "Not available"}
                            </Typography>
                        </Box>

                        <Divider />

                        <Typography
                            variant="h6"
                            fontWeight={700}
                        >
                            Delivery Partner
                        </Typography>

                        <Box>
                            <Typography color="text.secondary">
                                Name
                            </Typography>

                            <Typography fontWeight={600}>
                                {delivery.deliveryBoyName ||
                                    "Not available"}
                            </Typography>
                        </Box>

                    </Stack>
                )}
            </DialogContent>
        </Dialog>
    );
}

export default DeliveryStatusDialog;