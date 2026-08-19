import CloseIcon from "@mui/icons-material/Close";

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


function DeliveryOrderDetails({
    open,
    order,
    onClose
}) {

    if (!order) {
        return null;
    }


    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="md"
        >

            <DialogTitle>

                Order #{order.orderId}

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

                <Stack spacing={2}>

                    <Box>

                        <Typography>
                            <strong>
                                Order Date:
                            </strong>{" "}
                            {order.orderDate}
                        </Typography>

                        <Typography>
                            <strong>
                                Order Status:
                            </strong>{" "}
                            {order.orderStatus}
                        </Typography>

                        <Typography>
                            <strong>
                                Payment Status:
                            </strong>{" "}
                            {order.paymentStatus}
                        </Typography>

                        <Typography>
                            <strong>
                                Shipping Address:
                            </strong>{" "}
                            {order.shippingAddress}
                        </Typography>

                    </Box>


                    <Divider />


                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Order Items
                    </Typography>


                    {order.items?.map(item => (

                        <Box
                            key={item.medicineId}
                            sx={{
                                p: 2,
                                border:
                                    "1px solid #E5E7EB",
                                borderRadius: 2
                            }}
                        >

                            <Typography
                                fontWeight={600}
                            >
                                {item.medicineName}
                            </Typography>

                            <Typography>
                                Quantity: {item.quantity}
                            </Typography>

                            <Typography>
                                Subtotal: ₹
                                {Number(
                                    item.subTotal ?? 0
                                ).toFixed(2)}
                            </Typography>

                            <Typography>
                                Discount: ₹
                                {Number(
                                    item.discount ?? 0
                                ).toFixed(2)}
                            </Typography>

                            <Typography>
                                Tax: ₹
                                {Number(
                                    item.tax ?? 0
                                ).toFixed(2)}
                            </Typography>

                        </Box>

                    ))}


                    <Divider />


                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Total Amount: ₹
                        {Number(
                            order.totalAmount ?? 0
                        ).toFixed(2)}
                    </Typography>

                </Stack>

            </DialogContent>

        </Dialog>
    );
}
export default DeliveryOrderDetails;