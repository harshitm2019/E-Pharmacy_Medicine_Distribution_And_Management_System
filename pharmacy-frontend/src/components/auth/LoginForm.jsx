import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Stack, TextField, Typography } from "@mui/material";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";

import useAuth from "../../hooks/useAuth";
import { login as loginUser } from "../../services/authService";
import { loginSchema } from "../../validation/authSchemas";
import PasswordField from "./PasswordField";

function LoginForm() {

    const navigate = useNavigate();

    const { login } = useAuth();

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(loginSchema),
        defaultValues: {
            email: "",
            password: ""
        }
    });

    async function onSubmit(data) {

        try {

            const response = await loginUser(data);

            login(response.data);

            toast.success(response.message);

            setTimeout(() => {

                login(response.data);

                switch (response.data.role) {

                    case "ADMIN":
                        navigate("/admin/dashboard");
                        break;

                    case "CUSTOMER":
                        navigate("/customer/dashboard");
                        break;

                    case "DELIVERY_BOY":
                        navigate("/delivery/dashboard");
                        break;

                    default:
                        navigate("/");
                }

            }, 1000);

        } catch (error) {

            toast.error(error.response?.data?.message || "Login failed.");

        }

    }

    return (

        <form onSubmit={handleSubmit(onSubmit)}>

            <Stack spacing={3}>

                <TextField
                    fullWidth
                    label="Email"
                    {...register("email")}
                    error={!!errors.email}
                    helperText={errors.email?.message}
                />

                <PasswordField
                    label="Password"
                    register={register("password")}
                    error={!!errors.password}
                    helperText={errors.password?.message}
                />

                <Button
                    type="submit"
                    variant="contained"
                    size="large"
                    sx={{
                        py: 1.5,
                        borderRadius: "12px",
                        textTransform: "none",
                        fontWeight: 700
                    }}
                >
                    Login
                </Button>

                <Typography textAlign="center">
                    Don't have an account? <Link to="/register">Register</Link>
                </Typography>

            </Stack>

        </form>

    );

}

export default LoginForm;