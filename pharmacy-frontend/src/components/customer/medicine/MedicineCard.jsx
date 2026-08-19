import AddShoppingCartOutlinedIcon from "@mui/icons-material/AddShoppingCartOutlined";
import LocalPharmacyOutlinedIcon from "@mui/icons-material/LocalPharmacyOutlined";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { Box, Button, Card, CardContent, Chip, IconButton, Typography } from "@mui/material";
import { useState } from "react";

function MedicineCard({ medicine, onAddToCart }) {
    const [quantity, setQuantity] = useState(1);
    const outOfStock = medicine.stockQuantity <= 0;
    const prescriptionRequired = medicine.prescriptionNeed === "YES";

    function handleIncrement() {
        if (quantity < medicine.stockQuantity) {
            setQuantity(prev => prev + 1);
        }
    }

    function handleDecrement() {
        if (quantity > 1) {
            setQuantity(prev => prev - 1);
        }
    }

    return (
        <Card elevation={0} sx={{ height: "100%", border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden", display: "flex", flexDirection: "column", transition: ".3s", "&:hover": { transform: "translateY(-4px)", boxShadow: "0 12px 30px rgba(0,0,0,.08)" } }}>
            <Box sx={{ height: 210, bgcolor: "#F8FAFC", display: "flex", alignItems: "center", justifyContent: "center", p: 2 }}>
                {medicine.medicineImage ? (
                    <Box component="img" src={medicine.medicineImage} alt={medicine.medicineName} sx={{ width: "100%", height: "100%", objectFit: "contain" }} />
                ) : (
                    <LocalPharmacyOutlinedIcon sx={{ fontSize: 70, color: "#A5D6A7" }} />
                )}
            </Box>

            <CardContent sx={{ p: 2.5, display: "flex", flexDirection: "column", flex: 1 }}>
                <Typography variant="h6" fontWeight={700} sx={{ mb: 0.5 }}>
                    {medicine.medicineName}
                </Typography>

                <Typography variant="body2" color="text.secondary" sx={{ mb: 1.5 }}>
                    {medicine.manufacturer}
                </Typography>

                <Typography variant="body2" color="text.secondary" sx={{ mb: 1.5, display: "-webkit-box", WebkitLineClamp: 2, WebkitBoxOrient: "vertical", overflow: "hidden" }}>
                    {medicine.description || "No description available."}
                </Typography>

                <Box sx={{ display: "flex", alignItems: "center", gap: 1, flexWrap: "wrap", mb: 2 }}>
                    <Typography variant="h6" fontWeight={800} color="#2E7D32">
                        ₹{medicine.sellingPrice}
                    </Typography>

                    {medicine.price > medicine.sellingPrice && (
                        <Typography variant="body2" sx={{ textDecoration: "line-through", color: "#78909C" }}>
                            ₹{medicine.price}
                        </Typography>
                    )}

                    {medicine.discount > 0 && (
                        <Chip label={`${medicine.discount}% OFF`} size="small" color="success" />
                    )}
                </Box>

                <Box sx={{ display: "flex", gap: 1, flexWrap: "wrap", mb: 2 }}>
                    <Chip label={outOfStock ? "Out of Stock" : `${medicine.stockQuantity} in stock`} size="small" color={outOfStock ? "error" : "success"} variant="outlined" />

                    {prescriptionRequired && (
                        <Chip label="Prescription Required" size="small" color="warning" variant="outlined" />
                    )}
                </Box>

                {/* Quantity Control Editor Row */}
                {!outOfStock && (
                    <Box sx={{ display: "flex", alignItems: "center", justifyContent: "space-between", mb: 2, mt: "auto", border: "1px solid #E5E7EB", borderRadius: 2, p: 0.5 }}>
                        <IconButton size="small" onClick={handleDecrement} disabled={quantity <= 1}>
                            <RemoveIcon fontSize="small" />
                        </IconButton>
                        <Typography fontWeight={700}>{quantity}</Typography>
                        <IconButton size="small" onClick={handleIncrement} disabled={quantity >= medicine.stockQuantity}>
                            <AddIcon fontSize="small" />
                        </IconButton>
                    </Box>
                )}

                {/* Submit Action Button */}
                <Button 
                    fullWidth 
                    variant="contained" 
                    startIcon={<AddShoppingCartOutlinedIcon />} 
                    disabled={outOfStock} 
                    onClick={() => onAddToCart(medicine, quantity)} // Sends quantity back
                    sx={{ mt: outOfStock ? "auto" : 0, textTransform: "none", borderRadius: 2 }}
                >
                    {outOfStock ? "Out of Stock" : "Add to Cart"}
                </Button>
            </CardContent>
        </Card>
    );
}
export default MedicineCard;
