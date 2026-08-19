import { Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, TextField } from "@mui/material";
import { useState } from "react";
import CreateDeliveryBoyForm from "./CreateDeliveryBoyForm";

function CreateDeliveryBoyDialog({ open, onClose, users, onSubmit, loading }) {
    const [userId, setUserId] = useState("");

    function handleClose() {
        setUserId("");
        onClose();
    }

    function handleSubmit(data) {
        onSubmit(data);
    }

    return (
        <Dialog open={open} onClose={loading ? undefined : handleClose} fullWidth maxWidth="sm">
            <DialogTitle>Create Delivery Information</DialogTitle>

            <DialogContent>
                <TextField select fullWidth label="Delivery Boy" value={userId} onChange={event => setUserId(event.target.value)} disabled={loading} sx={{ mt: 1, mb: 2 }}>
                    <MenuItem value="">Select Delivery Boy</MenuItem>
                    {users.map(user => (
                        <MenuItem key={user.userId} value={user.userId}>
                            {user.email}
                        </MenuItem>
                    ))}
                </TextField>

                {userId && <CreateDeliveryBoyForm userId={Number(userId)} onSubmit={handleSubmit} />}
            </DialogContent>

            <DialogActions>
                <Button onClick={handleClose} color="inherit" disabled={loading}>Cancel</Button>
                <Button type="submit" form="create-delivery-boy-form" variant="contained" disabled={!userId || loading}>
                    {loading ? "Saving..." : "Save"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}
export default CreateDeliveryBoyDialog;