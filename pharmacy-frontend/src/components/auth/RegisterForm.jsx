import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Grid, MenuItem, TextField, Typography } from "@mui/material";
import { useForm } from "react-hook-form";
import { NavLink } from "react-router-dom";

import { INDIAN_STATES } from "../../constants/states";
import { registerSchema } from "../../validation/authSchemas";
import PasswordField from "./PasswordField";

import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { register as registerUser } from "../../services/authService";

function RegisterForm() {

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(registerSchema),
        defaultValues: {
            username: "",
            email: "",
            phone: "",
            password: "",
            address: "",
            city: "",
            state: "",
            pin: ""
        }
    });

    const navigate = useNavigate();

    async function onSubmit(data) {

        try {

            const response = await registerUser(data);

            toast.success(response.message);

            setTimeout(() => {
                navigate("/login");
            }, 1000);

        } catch (error) {

            toast.error(error.response?.data?.message || "Registration failed.");

        }
    }

    return (

        <form onSubmit={handleSubmit(onSubmit)}>

            <Grid container spacing={2}>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        fullWidth
                        label="Username"
                        {...register("username")}
                        error={!!errors.username}
                        helperText={errors.username?.message}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        fullWidth
                        label="Email"
                        {...register("email")}
                        error={!!errors.email}
                        helperText={errors.email?.message}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        fullWidth
                        label="Phone Number"
                        {...register("phone")}
                        error={!!errors.phone}
                        helperText={errors.phone?.message}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <PasswordField
                        label="Password"
                        register={register("password")}
                        error={!!errors.password}
                        helperText={errors.password?.message}
                    />
                </Grid>

                <Grid size={12}>
                    <TextField
                        fullWidth
                        multiline
                        rows={3}
                        label="Address"
                        {...register("address")}
                        error={!!errors.address}
                        helperText={errors.address?.message}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        fullWidth
                        label="City"
                        {...register("city")}
                        error={!!errors.city}
                        helperText={errors.city?.message}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        select
                        fullWidth
                        label="State"
                        defaultValue=""
                        {...register("state")}
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
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField
                        fullWidth
                        label="PIN Code"
                        {...register("pin")}
                        error={!!errors.pin}
                        helperText={errors.pin?.message}
                    />
                </Grid>

                <Grid size={12}>
                    <Button
                        fullWidth
                        type="submit"
                        variant="contained"
                        size="large"
                        sx={{ py: 1.6, mt: 2, borderRadius: "12px", textTransform: "none", fontWeight: 700 }}
                    >
                        Create Account
                    </Button>
                </Grid>

                <Grid size={12}>
                    <Typography textAlign="center">
                        Already have an account?{" "}
                        <NavLink to="/login">Login</NavLink>
                    </Typography>
                </Grid>
            </Grid>
        </form>
    );
}
export default RegisterForm;