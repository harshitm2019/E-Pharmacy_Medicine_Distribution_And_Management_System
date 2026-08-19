import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { z } from "zod";

const schema = z.object({
    vehicleNo: z.string().trim().regex(/^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$/, "Invalid vehicle number.")
});

function CreateDeliveryBoyForm({ userId, onSubmit }) {
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(schema),
        defaultValues: { vehicleNo: "" }
    });

    return (
        <form id="create-delivery-boy-form" onSubmit={handleSubmit(data => onSubmit({ userId, ...data }))}>
            <Grid container spacing={2} sx={{ pt: 1 }}>
                <Grid size={12}>
                    <TextField fullWidth label="Vehicle Number" placeholder="DL01AB1234" {...register("vehicleNo")} error={!!errors.vehicleNo} helperText={errors.vehicleNo?.message} />
                </Grid>
            </Grid>
        </form>
    );
}

export default CreateDeliveryBoyForm;