import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import UpdateDeliveryBoyForm from "./UpdateDeliveryBoyForm";

function UpdateDeliveryBoyDialog({ open, onClose, deliveryBoy, onSubmit, loading }) {
    return (
        <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="sm">
            <DialogTitle>Edit Delivery Boy</DialogTitle>
            <DialogContent>
                <UpdateDeliveryBoyForm deliveryBoy={deliveryBoy} onSubmit={onSubmit} />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="inherit" disabled={loading}>Cancel</Button>
                <Button type="submit" form="update-delivery-boy-form" variant="contained" disabled={loading}>{loading ? "Saving..." : "Save"}</Button>
            </DialogActions>
        </Dialog>
    );
}

export default UpdateDeliveryBoyDialog;