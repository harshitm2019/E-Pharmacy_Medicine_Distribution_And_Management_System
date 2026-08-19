import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import CreateUserForm from "./CreateUserForm";

function CreateUserDialog({ open, onClose, onSubmit, loading }) {
    return (
        <Dialog open={open} onClose={loading ? undefined : onClose} fullWidth maxWidth="sm">
            <DialogTitle>Create User</DialogTitle>

            <DialogContent>
                <CreateUserForm onSubmit={onSubmit} />
            </DialogContent>

            <DialogActions sx={{mb:2}}>
                <Button onClick={onClose} color="inherit" disabled={loading}>
                    Cancel
                </Button>

                <Button type="submit" form="create-user-form" variant="contained" disabled={loading}>
                    {loading ? "Creating..." : "Create User"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default CreateUserDialog;