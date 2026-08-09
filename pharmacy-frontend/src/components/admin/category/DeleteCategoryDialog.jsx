import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material";

function DeleteCategoryDialog({ open, category, onClose, onConfirm, loading }) {
    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>Delete Category</DialogTitle>

            <DialogContent>
                <Typography>
                    Are you sure you want to delete <strong>{category?.categoryName}</strong>?
                </Typography>
            </DialogContent>

            <DialogActions sx={{ px: 3, pb: 3 }}>
                <Button onClick={onClose} disabled={loading}>Cancel</Button>
                <Button onClick={onConfirm} color="error" variant="contained" disabled={loading}>
                    {loading ? "Deleting..." : "Delete"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default DeleteCategoryDialog;