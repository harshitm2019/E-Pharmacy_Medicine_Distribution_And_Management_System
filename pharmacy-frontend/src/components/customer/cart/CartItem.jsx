import DeleteOutlineOutlinedIcon from "@mui/icons-material/DeleteOutlineOutlined";
import RemoveOutlinedIcon from "@mui/icons-material/RemoveOutlined";
import AddOutlinedIcon from "@mui/icons-material/AddOutlined";

import {
    Box,
    Button,
    Card,
    CardContent,
    Chip,
    IconButton,
    Stack,
    Typography
} from "@mui/material";

function CartItem({
    item,
    onIncrease,
    onDecrease,
    onRemove
}) {
    const subtotal =
        Number(item.sellingPrice) * item.quantity;

    const isPrescriptionRequired =
        item.prescriptionNeed === "Yes" ||
        item.prescriptionNeed === "YES";

    return (
        <Card
            elevation={0}
            sx={{
                border: "1px solid #E5E7EB",
                borderRadius: 3
            }}
        >
            <CardContent sx={{ p: 2.5 }}>
                <Box
                    sx={{
                        display: "flex",
                        gap: 2,
                        alignItems: "center"
                    }}
                >
                    <Box
                        sx={{
                            width: 90,
                            height: 90,
                            borderRadius: 2,
                            bgcolor: "#F5F7FA",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            flexShrink: 0,
                            overflow: "hidden"
                        }}
                    >
                        {item.medicineImage ? (
                            <Box
                                component="img"
                                src={item.medicineImage}
                                alt={item.medicineName}
                                sx={{
                                    width: "100%",
                                    height: "100%",
                                    objectFit: "cover"
                                }}
                            />
                        ) : (
                            <Typography
                                color="text.secondary"
                                variant="caption"
                            >
                                No Image
                            </Typography>
                        )}
                    </Box>

                    <Box sx={{ flex: 1, minWidth: 0 }}>
                        <Typography
                            fontWeight={700}
                            sx={{
                                overflow: "hidden",
                                textOverflow: "ellipsis",
                                whiteSpace: "nowrap"
                            }}
                        >
                            {item.medicineName}
                        </Typography>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            {item.manufacturer}
                        </Typography>

                        {isPrescriptionRequired && (
                            <Chip
                                label="Prescription Required"
                                size="small"
                                sx={{
                                    mt: 1,
                                    bgcolor: "#FFF3E0",
                                    color: "#E65100"
                                }}
                            />
                        )}

                        <Typography
                            sx={{
                                mt: 1,
                                fontWeight: 700,
                                color: "#2E7D32"
                            }}
                        >
                            ₹{item.sellingPrice}
                        </Typography>
                    </Box>

                    <Stack
                        alignItems="flex-end"
                        spacing={1.5}
                    >
                        <IconButton
                            size="small"
                            color="error"
                            onClick={() =>
                                onRemove(item.medicineId)
                            }
                        >
                            <DeleteOutlineOutlinedIcon />
                        </IconButton>

                        <Box
                            sx={{
                                display: "flex",
                                alignItems: "center",
                                border: "1px solid #D1D5DB",
                                borderRadius: 2
                            }}
                        >
                            <IconButton
                                size="small"
                                onClick={() =>
                                    onDecrease(
                                        item.medicineId
                                    )
                                }
                            >
                                <RemoveOutlinedIcon fontSize="small" />
                            </IconButton>

                            <Typography
                                sx={{
                                    minWidth: 35,
                                    textAlign: "center",
                                    fontWeight: 700
                                }}
                            >
                                {item.quantity}
                            </Typography>

                            <IconButton
                                size="small"
                                disabled={
                                    item.quantity >=
                                    item.stockQuantity
                                }
                                onClick={() =>
                                    onIncrease(
                                        item.medicineId
                                    )
                                }
                            >
                                <AddOutlinedIcon fontSize="small" />
                            </IconButton>
                        </Box>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            ₹{subtotal.toFixed(2)}
                        </Typography>
                    </Stack>
                </Box>
            </CardContent>
        </Card>
    );
}

export default CartItem;