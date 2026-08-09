import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material";

function PrescriptionDecisionDialog({ open, order, status, onClose, onConfirm, loading }) {
    const approved = status === "APPROVED";

    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>{approved ? "Approve Prescription" : "Reject Prescription"}</DialogTitle>

            <DialogContent>
                <Typography>
                    Are you sure you want to {approved ? "approve" : "reject"} the prescription for order <strong>#{order?.orderId}</strong>?
                </Typography>
            </DialogContent>

            <DialogActions sx={{ px: 3, pb: 3 }}>
                <Button onClick={onClose} disabled={loading}>Cancel</Button>
                <Button onClick={onConfirm} color={approved ? "success" : "error"} variant="contained" disabled={loading}>
                    {loading ? "Updating..." : approved ? "Approve" : "Reject"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default PrescriptionDecisionDialog;