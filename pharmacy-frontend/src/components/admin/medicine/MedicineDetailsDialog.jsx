import CloseIcon from "@mui/icons-material/Close";
import { Dialog, DialogContent, DialogTitle, IconButton, Stack, Typography } from "@mui/material";

function MedicineDialog({ open, medicine, onClose }) {
    if (!medicine) return null;

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
            <DialogTitle>
                Medicine Details
                <IconButton onClick={onClose} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent>
                <Stack spacing={2}>
                    {medicine.medicineImage && (
                        <img src={medicine.medicineImage} alt={medicine.medicineName} style={{ width: 140, height: 140, objectFit: "contain", borderRadius: 12 }} />
                    )}

                    <Typography><strong>Name:</strong> {medicine.medicineName}</Typography>
                    <Typography><strong>Category:</strong> {medicine.categoryName}</Typography>
                    <Typography><strong>Manufacturer:</strong> {medicine.manufacturer}</Typography>
                    <Typography><strong>Batch Number:</strong> {medicine.batchNumber}</Typography>
                    <Typography><strong>Manufacture Date:</strong> {medicine.manufactureDate}</Typography>
                    <Typography><strong>Expiry Date:</strong> {medicine.expiryDate}</Typography>
                    <Typography><strong>Price:</strong> ₹{medicine.price}</Typography>
                    <Typography><strong>Discount:</strong> {medicine.discount}%</Typography>
                    <Typography><strong>Selling Price:</strong> ₹{medicine.sellingPrice}</Typography>
                    <Typography><strong>Stock:</strong> {medicine.stockQuantity}</Typography>
                    <Typography><strong>Prescription Required:</strong> {medicine.prescriptionNeed}</Typography>
                    <Typography><strong>Status:</strong> {medicine.status}</Typography>
                    <Typography><strong>Description:</strong> {medicine.description}</Typography>
                    <Typography><strong>Created:</strong> {medicine.createdAt}</Typography>
                    <Typography><strong>Updated:</strong> {medicine.updatedAt}</Typography>
                </Stack>
            </DialogContent>
        </Dialog>
    );
}

export default MedicineDialog;