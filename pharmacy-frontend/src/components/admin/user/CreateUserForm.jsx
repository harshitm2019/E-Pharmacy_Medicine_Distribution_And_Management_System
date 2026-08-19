import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, MenuItem, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { INDIAN_STATES } from "../../../constants/states";
import { createUserSchema } from "../../../validation/createUserSchema";
import PasswordField from "../../auth/PasswordField";

function CreateUserForm({ onSubmit }) {
    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(createUserSchema),
        defaultValues: {
            username: "",
            email: "",
            password: "",
            phoneNumber: "",
            address: "",
            city: "",
            state: "",
            pin: "",
            role: ""
        }
    });

    return (
        <form id="create-user-form" onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={2} sx={{ pt: 1 }}>
                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Username" {...register("username")} error={!!errors.username} helperText={errors.username?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Email" {...register("email")} error={!!errors.email} helperText={errors.email?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Phone Number" {...register("phoneNumber")} error={!!errors.phoneNumber} helperText={errors.phoneNumber?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <PasswordField label="Password" register={register("password")} error={!!errors.password} helperText={errors.password?.message} />
                </Grid>

                <Grid size={12}>
                    <TextField fullWidth multiline rows={3} label="Address" {...register("address")} error={!!errors.address} helperText={errors.address?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="City" {...register("city")} error={!!errors.city} helperText={errors.city?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField select fullWidth label="State" defaultValue="" {...register("state")} error={!!errors.state} helperText={errors.state?.message}>
                        <MenuItem value="">Select State</MenuItem>
                        {INDIAN_STATES.map(state => <MenuItem key={state} value={state}>{state}</MenuItem>)}
                    </TextField>
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="PIN Code" {...register("pin")} error={!!errors.pin} helperText={errors.pin?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField select fullWidth label="Role" defaultValue="" {...register("role")} error={!!errors.role} helperText={errors.role?.message}>
                        <MenuItem value="">Select Role</MenuItem>
                        <MenuItem value="ADMIN">Admin</MenuItem>
                        <MenuItem value="DELIVERY_BOY">Delivery Boy</MenuItem>
                    </TextField>
                </Grid>
            </Grid>
        </form>
    );
}

export default CreateUserForm;