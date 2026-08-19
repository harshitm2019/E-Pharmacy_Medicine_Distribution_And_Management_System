import CloseIcon from "@mui/icons-material/Close";

import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    Stack,
    TextField,
    Typography
} from "@mui/material";

import { useEffect, useState } from "react";


function ReturnRequestDialog({
    open,
    order,
    onClose,
    onSubmit,
    loading
}) {

    const [returnReason, setReturnReason] =
        useState("");


    useEffect(() => {

        if (open) {
            setReturnReason("");
        }

    }, [open]);


    if (!order) {
        return null;
    }


    function handleSubmit() {

        const reason =
            returnReason.trim();

        if (!reason) {
            return;
        }


        onSubmit({
            orderId: order.orderId,
            returnReason: reason
        });
    }


    return (
        <Dialog
            open={open}
            onClose={loading ? undefined : onClose}
            fullWidth
            maxWidth="sm"
        >

            <DialogTitle>

                Request Return

                <IconButton
                    onClick={onClose}
                    disabled={loading}
                    sx={{
                        position: "absolute",
                        right: 8,
                        top: 8
                    }}
                >
                    <CloseIcon />
                </IconButton>

            </DialogTitle>


            <DialogContent sx={{ pt: 2 }}>

                <Stack spacing={3}>

                    <Typography>
                        <strong>
                            Order:
                        </strong>{" "}
                        #{order.orderId}
                    </Typography>


                    <Typography>
                        <strong>
                            Order Amount:
                        </strong>{" "}
                        ₹
                        {Number(
                            order.totalAmount ?? 0
                        ).toFixed(2)}
                    </Typography>


                    <TextField
                        label="Return Reason"
                        placeholder="Enter reason for returning this order"
                        value={returnReason}
                        onChange={event =>
                            setReturnReason(
                                event.target.value
                            )
                        }
                        multiline
                        minRows={4}
                        fullWidth
                        inputProps={{
                            maxLength: 500
                        }}
                        helperText={`${returnReason.length}/500`}
                        disabled={loading}
                    />

                </Stack>

            </DialogContent>


            <DialogActions
                sx={{
                    px: 3,
                    pb: 3
                }}
            >

                <Button
                    variant="outlined"
                    onClick={onClose}
                    disabled={loading}
                >
                    Cancel
                </Button>


                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={
                        loading ||
                        returnReason.trim().length < 10
                    }
                >
                    {loading
                        ? "Submitting..."
                        : "Submit Return"}
                </Button>

            </DialogActions>

        </Dialog>
    );
}
export default ReturnRequestDialog;