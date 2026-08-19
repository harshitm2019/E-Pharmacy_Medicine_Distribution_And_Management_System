import DeleteSweepOutlinedIcon from "@mui/icons-material/DeleteSweepOutlined";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";

import {
    Box,
    Button,
    Card,
    CardContent,
    Divider,
    Stack,
    Typography
} from "@mui/material";

import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

import CartItem from "../../components/customer/cart/CartItem";
import useCart from "../../hooks/customer/useCart";

function Cart() {
    const navigate = useNavigate();

    const {
        cart,
        totalItems,
        totalAmount,
        hasPrescriptionMedicine,
        increaseQuantity,
        decreaseQuantity,
        removeFromCart,
        clearCart
    } = useCart();

    function handleClearCart() {
        clearCart();

        toast.success("Cart cleared.");
    }

    function handleCheckout() {
        if (cart.length === 0) {
            toast.error("Your cart is empty.");
            return;
        }

        const invalidStock = cart.some(
            item =>
                item.quantity > item.stockQuantity ||
                item.stockQuantity <= 0
        );

        if (invalidStock) {
            toast.error(
                "One or more medicines are no longer available in the selected quantity."
            );
            return;
        }

        navigate("/customer/checkout");
    }

    if (cart.length === 0) {
        return (
            <Box>
                <Typography
                    variant="h4"
                    fontWeight={700}
                    sx={{ mb: 1 }}
                >
                    My Cart
                </Typography>

                <Typography
                    color="text.secondary"
                    sx={{ mb: 4 }}
                >
                    Review your medicines before checkout.
                </Typography>

                <Box
                    sx={{
                        textAlign: "center",
                        py: 10
                    }}
                >
                    <ShoppingCartOutlinedIcon
                        sx={{
                            fontSize: 80,
                            color: "#B0BEC5",
                            mb: 2
                        }}
                    />

                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Your cart is empty
                    </Typography>

                    <Typography
                        color="text.secondary"
                        sx={{ mt: 1, mb: 3 }}
                    >
                        Add medicines to your cart to
                        continue.
                    </Typography>

                    <Button
                        variant="contained"
                        onClick={() =>
                            navigate(
                                "/customer/medicines"
                            )
                        }
                    >
                        Browse Medicines
                    </Button>
                </Box>
            </Box>
        );
    }

    return (
        <Box>
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: {
                        xs: "flex-start",
                        sm: "center"
                    },
                    flexDirection: {
                        xs: "column",
                        sm: "row"
                    },
                    gap: 2,
                    mb: 4
                }}
            >
                <Box>
                    <Typography
                        variant="h4"
                        fontWeight={700}
                        sx={{ mb: 1 }}
                    >
                        My Cart
                    </Typography>

                    <Typography color="text.secondary">
                        {totalItems}{" "}
                        {totalItems === 1
                            ? "item"
                            : "items"}{" "}
                        in your cart.
                    </Typography>
                </Box>

                <Button
                    color="error"
                    variant="outlined"
                    startIcon={
                        <DeleteSweepOutlinedIcon />
                    }
                    onClick={handleClearCart}
                >
                    Clear Cart
                </Button>
            </Box>

            <Box
                sx={{
                    display: "grid",
                    gridTemplateColumns: {
                        xs: "1fr",
                        lg: "minmax(0, 1fr) 360px"
                    },
                    gap: 3,
                    alignItems: "start"
                }}
            >
                <Stack spacing={2}>
                    {cart.map(item => (
                        <CartItem
                            key={item.medicineId}
                            item={item}
                            onIncrease={
                                increaseQuantity
                            }
                            onDecrease={
                                decreaseQuantity
                            }
                            onRemove={
                                removeFromCart
                            }
                        />
                    ))}
                </Stack>

                <Card
                    elevation={0}
                    sx={{
                        border:
                            "1px solid #E5E7EB",
                        borderRadius: 3,
                        position: {
                            lg: "sticky"
                        },
                        top: {
                            lg: 100
                        }
                    }}
                >
                    <CardContent sx={{ p: 3 }}>
                        <Typography
                            variant="h6"
                            fontWeight={700}
                            sx={{ mb: 3 }}
                        >
                            Order Summary
                        </Typography>

                        <Stack spacing={2}>
                            <Box
                                sx={{
                                    display: "flex",
                                    justifyContent:
                                        "space-between"
                                }}
                            >
                                <Typography>
                                    Items
                                </Typography>

                                <Typography fontWeight={600}>
                                    {totalItems}
                                </Typography>
                            </Box>

                            <Box
                                sx={{
                                    display: "flex",
                                    justifyContent:
                                        "space-between"
                                }}
                            >
                                <Typography>
                                    Subtotal
                                </Typography>

                                <Typography fontWeight={600}>
                                    ₹
                                    {totalAmount.toFixed(
                                        2
                                    )}
                                </Typography>
                            </Box>

                            <Divider />

                            <Box
                                sx={{
                                    display: "flex",
                                    justifyContent:
                                        "space-between"
                                }}
                            >
                                <Typography
                                    fontWeight={700}
                                >
                                    Total
                                </Typography>

                                <Typography
                                    fontWeight={800}
                                    fontSize={20}
                                    color="#2E7D32"
                                >
                                    ₹
                                    {totalAmount.toFixed(
                                        2
                                    )}
                                </Typography>
                            </Box>

                            {hasPrescriptionMedicine && (
                                <Typography
                                    variant="body2"
                                    color="#E65100"
                                    sx={{
                                        bgcolor:
                                            "#FFF3E0",
                                        p: 1.5,
                                        borderRadius: 2
                                    }}
                                >
                                    One or more medicines
                                    require a valid
                                    prescription. You
                                    will be asked to
                                    upload one during
                                    checkout.
                                </Typography>
                            )}

                            <Button
                                fullWidth
                                variant="contained"
                                size="large"
                                onClick={
                                    handleCheckout
                                }
                                sx={{
                                    mt: 1,
                                    py: 1.4,
                                    fontWeight: 700
                                }}
                            >
                                Proceed to Checkout
                            </Button>
                        </Stack>
                    </CardContent>
                </Card>
            </Box>
        </Box>
    );
}

export default Cart;