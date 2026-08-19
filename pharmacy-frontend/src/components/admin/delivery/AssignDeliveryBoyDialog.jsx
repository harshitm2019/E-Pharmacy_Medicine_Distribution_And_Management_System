import { Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";
import useAssignDeliveryBoy from "../../../hooks/admin/useAssignDeliveryBoy";
import useAvailableDeliveryBoys from "../../../hooks/admin/useAvailableDeliveryBoys";

function AssignDeliveryBoyDialog({ open, orderId, onClose }) {
    const [deliveryBoyId, setDeliveryBoyId] = useState("");
    const { data, isLoading } = useAvailableDeliveryBoys({ page: 0, size: 100, enabled: open });
    const assignMutation = useAssignDeliveryBoy();

    const deliveryBoys = data?.data?.content ?? [];

    function handleAssign() {
        assignMutation.mutate(
            { orderId, deliveryBoyId: Number(deliveryBoyId) },
            {
                onSuccess: (response) => {
                    toast.success(response.message);
                    setDeliveryBoyId("");
                    onClose();
                },
                onError: error => {
                    toast.error(error.response?.data?.message || "Failed to assign delivery boy.");
                }
            }
        );
    }

    function handleClose() {
        setDeliveryBoyId("");
        onClose();
    }

    return (
        <Dialog open={open} onClose={assignMutation.isPending ? undefined : handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Assign Delivery Boy</DialogTitle>

            <DialogContent>
                <TextField fullWidth label="Order ID" value={orderId || ""} disabled sx={{ mt: 1, mb: 2 }} />

                <TextField select fullWidth label="Delivery Boy" value={deliveryBoyId}
                    onChange={event => setDeliveryBoyId(event.target.value)}
                    disabled={isLoading || assignMutation.isPending}>
                    <MenuItem value="">Select Delivery Boy</MenuItem>
                    {deliveryBoys.map(deliveryBoy => <MenuItem key={deliveryBoy.deliveryBoyId}
                        value={deliveryBoy.deliveryBoyId}>{deliveryBoy.vehicleNo} — {deliveryBoy.username}</MenuItem>)}
                </TextField>
            </DialogContent>

            <DialogActions>
                <Button onClick={handleClose} color="inherit" disabled={assignMutation.isPending}>Cancel</Button>
                <Button variant="contained" onClick={handleAssign}
                    disabled={!deliveryBoyId || isLoading || assignMutation.isPending}>
                    {assignMutation.isPending ? "Assigning..." : "Assign"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}
export default AssignDeliveryBoyDialog;