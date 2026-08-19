import CloseIcon from "@mui/icons-material/Close";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";

function PrescriptionFormDialog({ open, mode, prescription, onClose, onSubmit, loading }) {
    const [doctorName, setDoctorName] = useState("");
    const [file, setFile] = useState(null);

    const isReplace = mode === "replace";

    useEffect(() => {
        if (open) {
            setDoctorName(prescription?.doctorName || "");
            setFile(null);
        }
    }, [open, prescription]);

    function handleSubmit() {
        if (!file) return;

        if (isReplace) {
            onSubmit({
                prescriptionId: prescription.prescriptionId,
                file
            });
            return;
        }

        if (!doctorName.trim()) return;

        onSubmit({
            doctorName: doctorName.trim(),
            file
        });
    }

    return (
        <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="sm">
            <DialogTitle>
                {isReplace ? "Replace Prescription" : "Upload Prescription"}

                <IconButton onClick={onClose} disabled={loading} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>
                {!isReplace && (
                    <TextField
                        fullWidth
                        label="Doctor Name"
                        value={doctorName}
                        onChange={event => setDoctorName(event.target.value)}
                        disabled={loading}
                        sx={{ mb: 3 }}
                    />
                )}

                {isReplace && (
                    <Typography color="text.secondary" sx={{ mb: 2 }}>
                        Replace prescription for Dr. {prescription?.doctorName}
                    </Typography>
                )}

                <Typography fontWeight={600} sx={{ mb: 1 }}>
                    Prescription File
                </Typography>

                <Button component="label" variant="outlined" disabled={loading}>
                    Choose File
                    <input
                        hidden
                        type="file"
                        accept="image/*,.pdf"
                        onChange={event => setFile(event.target.files?.[0] || null)}
                    />
                </Button>

                {file && (
                    <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                        {file.name}
                    </Typography>
                )}
            </DialogContent>

            <DialogActions sx={{m:2}}>
                <Button onClick={onClose} disabled={loading} color="inherit">
                    Cancel
                </Button>

                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={!file || (!isReplace && !doctorName.trim()) || loading}
                >
                    {loading ? (isReplace ? "Replacing..." : "Uploading...") : isReplace ? "Replace" : "Upload"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}
export default PrescriptionFormDialog;