import CloseIcon from "@mui/icons-material/Close";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, Stack, Typography } from "@mui/material";

function PrescriptionDetailsDialog({ open, prescription, onClose }) {
    if (!prescription) return null;

    function openPrescription() {
        window.open(prescription.prescriptionUrl, "_blank", "noopener,noreferrer");
    }

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
            <DialogTitle>
                Prescription Details
                <IconButton onClick={onClose} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 3 }}>
                <Stack spacing={2}>
                    <Typography><strong>Prescription ID:</strong> {prescription.prescriptionId}</Typography>
                    <Typography><strong>Doctor:</strong> {prescription.doctorName}</Typography>
                    <Typography><strong>Status:</strong> {prescription.status}</Typography>
                    <Typography><strong>Uploaded:</strong> {prescription.uploadedDate}</Typography>
                </Stack>
            </DialogContent>

            <DialogActions sx={{ px: 3, pb: 3 }}>
                <Button onClick={onClose} color="inherit">Close</Button>

                {prescription.prescriptionUrl && (
                    <Button variant="contained" onClick={openPrescription}>
                        View Prescription
                    </Button>
                )}
            </DialogActions>
        </Dialog>
    );
}

export default PrescriptionDetailsDialog;