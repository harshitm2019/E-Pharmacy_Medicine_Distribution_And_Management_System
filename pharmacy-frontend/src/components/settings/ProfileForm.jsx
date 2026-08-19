import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Grid, MenuItem, TextField } from "@mui/material";
import { useEffect } from "react";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";

import { INDIAN_STATES } from "../../constants/states";

const profileSchema = z.object({
    username: z.string().trim().min(3, "Username must be at least 3 characters.").max(100, "Username must not exceed 100 characters."),
    phoneNumber: z.string().regex(/^[6-9][0-9]{9}$/, "Invalid phone number."),
    address: z.string().trim().min(1, "Address is required."),
    city: z.string().trim().min(1, "City is required."),
    state: z.string().min(1, "State is required."),
    pin: z.string().regex(/^[1-9][0-9]{5}$/, "Invalid PIN code.")
});

function ProfileForm({ profile, onSubmit, loading }) {
    const { register, handleSubmit, reset, control, formState: { errors } } = useForm({
        resolver: zodResolver(profileSchema),
        defaultValues: {
            username: "",
            phoneNumber: "",
            address: "",
            city: "",
            state: "",
            pin: ""
        }
    });

    useEffect(() => {
        if (profile) {
            reset({
                username: profile.username || "",
                phoneNumber: profile.phoneNumber || "",
                address: profile.address || "",
                city: profile.city || "",
                state: profile.state || "",
                pin: profile.pin || ""
            });
        }
    }, [profile, reset]);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={2}>
                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Username" {...register("username")} error={!!errors.username} helperText={errors.username?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Phone Number" {...register("phoneNumber")} error={!!errors.phoneNumber} helperText={errors.phoneNumber?.message} />
                </Grid>

                <Grid size={12}>
                    <TextField fullWidth multiline rows={3} label="Address" {...register("address")} error={!!errors.address} helperText={errors.address?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="City" {...register("city")} error={!!errors.city} helperText={errors.city?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <Controller
                        name="state"
                        control={control}
                        render={({ field }) => (
                            <TextField
                                {...field}
                                select
                                fullWidth
                                label="State"
                                error={!!errors.state}
                                helperText={errors.state?.message}
                            >
                                <MenuItem value="">Select State</MenuItem>
                                {INDIAN_STATES.map(state => (
                                    <MenuItem key={state} value={state}>
                                        {state}
                                    </MenuItem>
                                ))}
                            </TextField>
                        )}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="PIN Code" {...register("pin")} error={!!errors.pin} helperText={errors.pin?.message} />
                </Grid>

                <Grid size={12}>
                    <Button type="submit" variant="contained" disabled={loading} sx={{ textTransform: "none" }}>
                        {loading ? "Updating..." : "Update Profile"}
                    </Button>
                </Grid>
            </Grid>
        </form>
    );
}
export default ProfileForm;