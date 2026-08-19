import AssignmentReturnOutlinedIcon
    from "@mui/icons-material/AssignmentReturnOutlined";

import {
    Box,
    Button,
    Card,
    CardContent,
    Chip,
    Stack,
    Typography
} from "@mui/material";


function EligibleReturnOrders({
    orders,
    onRequestReturn
}) {

    if (orders.length === 0) {

        return (
            <Box
                sx={{
                    textAlign: "center",
                    py: 8
                }}
            >

                <AssignmentReturnOutlinedIcon
                    sx={{
                        fontSize: 60,
                        color: "text.secondary",
                        mb: 2
                    }}
                />

                <Typography
                    variant="h6"
                    fontWeight={700}
                >
                    No Orders Eligible for Return
                </Typography>


                <Typography
                    color="text.secondary"
                    sx={{ mt: 1 }}
                >
                    Only delivered orders can be
                    returned.
                </Typography>

            </Box>
        );
    }


    return (
        <Box
            sx={{
                display: "grid",

                gridTemplateColumns: {
                    xs: "1fr",
                    sm: "repeat(2, minmax(0, 1fr))"
                },

                gap: 3
            }}
        >

            {orders.map(order => (

                <Card
                    key={order.orderId}
                    elevation={0}
                    sx={{
                        border:
                            "1px solid #E5E7EB",
                        borderRadius: 3
                    }}
                >

                    <CardContent
                        sx={{
                            p: 3,
                            "&:last-child": {
                                pb: 3
                            }
                        }}
                    >

                        <Box
                            sx={{
                                display: "flex",
                                justifyContent:
                                    "space-between",
                                alignItems:
                                    "flex-start",
                                gap: 2,
                                mb: 2
                            }}
                        >

                            <Box>

                                <Typography
                                    fontWeight={700}
                                >
                                    Order #
                                    {order.orderId}
                                </Typography>


                                <Typography
                                    variant="body2"
                                    color="text.secondary"
                                >
                                    {order.orderDate
                                        ? new Date(
                                            order.orderDate
                                        ).toLocaleString()
                                        : "—"}
                                </Typography>

                            </Box>


                            <Chip
                                label="DELIVERED"
                                color="success"
                                size="small"
                            />

                        </Box>


                        <Stack spacing={1} sx={{ mb: 3 }}>
                            <Typography>
                                <strong>Total:</strong>{" "}
                                ₹
                                {Number(
                                    order.totalAmount ?? 0
                                ).toFixed(2)}
                            </Typography>
                        </Stack>


                        <Button
                            variant="contained"
                            startIcon={
                                <AssignmentReturnOutlinedIcon />
                            }
                            onClick={() =>
                                onRequestReturn(order)
                            }
                        >
                            Request Return
                        </Button>

                    </CardContent>

                </Card>

            ))}

        </Box>
    );
}


export default EligibleReturnOrders;