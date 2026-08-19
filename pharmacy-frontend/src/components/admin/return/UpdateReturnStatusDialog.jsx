import CloseIcon from "@mui/icons-material/Close";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, MenuItem, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";

function UpdateReturnStatusDialog({ open, returnOrder, onClose, onConfirm, loading }) {
    const [status, setStatus] = useState("");

    useEffect(() => {
        if (open) {
            setStatus(returnOrder?.returnStatus === "PENDING" ? "APPROVED" : "REFUNDED");
        }
    }, [open, returnOrder]);

    if (!returnOrder) return null;

    const availableStatuses = returnOrder.returnStatus === "PENDING"
        ? ["APPROVED", "REJECTED"]
        : ["REFUNDED"];

    return (
        <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="sm">
            <DialogTitle>
                Update Return Status
                <IconButton onClick={onClose} disabled={loading} sx={{ position: "absolute", right: 8, top: 8 }}>
                    <CloseIcon />
                </IconButton>
            </DialogTitle>

            <DialogContent sx={{ pt: 2 }}>
                <Typography sx={{ mb: 2 }}>
                    <strong>Return ID:</strong> {returnOrder.returnId}
                </Typography>

                <Typography sx={{ mb: 2 }}>
                    <strong>Order ID:</strong> {returnOrder.orderId}
                </Typography>

                <Typography sx={{ mb: 2 }}>
                    <strong>Current Status:</strong> {returnOrder.returnStatus}
                </Typography>

                <TextField
                    select
                    fullWidth
                    label="New Status"
                    value={status}
                    onChange={event => setStatus(event.target.value)}
                    disabled={loading}
                >
                    {availableStatuses.map(value => (
                        <MenuItem key={value} value={value}>
                            {value}
                        </MenuItem>
                    ))}
                </TextField>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose} color="inherit" disabled={loading}>
                    Cancel
                </Button>

                <Button
                    variant="contained"
                    onClick={() => onConfirm(status)}
                    disabled={!status || loading}
                >
                    {loading ? "Updating..." : "Update"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}
export default UpdateReturnStatusDialog;