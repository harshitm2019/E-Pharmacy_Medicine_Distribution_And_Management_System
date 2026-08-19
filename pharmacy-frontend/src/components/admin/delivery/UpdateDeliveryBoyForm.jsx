import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, MenuItem, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { z } from "zod";

const updateDeliveryBoySchema = z.object({
    vehicleNo: z.string().trim().regex(/^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$/, "Invalid vehicle number."),
    status: z.enum(["ACTIVE", "INACTIVE"], { message: "Status is required." })
});

function UpdateDeliveryBoyForm({ deliveryBoy, onSubmit }) {
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(updateDeliveryBoySchema),
        defaultValues: {
            vehicleNo: deliveryBoy?.vehicleNo || "",
            status: deliveryBoy?.status || ""
        }
    });

    return (
        <form id="update-delivery-boy-form" onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={2} sx={{ pt: 1 }}>
                <Grid size={12}>
                    <TextField fullWidth label="Vehicle Number" {...register("vehicleNo")} error={!!errors.vehicleNo} helperText={errors.vehicleNo?.message} />
                </Grid>
                <Grid size={12}>
                    <TextField select fullWidth label="Status" defaultValue={deliveryBoy?.status || ""} {...register("status")} error={!!errors.status}
                     helperText={errors.status?.message}>
                        <MenuItem value="ACTIVE">Active</MenuItem>
                        <MenuItem value="INACTIVE">Inactive</MenuItem>
                    </TextField>
                </Grid>
            </Grid>
        </form>
    );
}

export default UpdateDeliveryBoyForm;