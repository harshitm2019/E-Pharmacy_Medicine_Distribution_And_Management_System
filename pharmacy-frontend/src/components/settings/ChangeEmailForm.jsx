import { zodResolver } from "@hookform/resolvers/zod";
import { Button, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { z } from "zod";

const changeEmailSchema = z.object({
    newEmail: z.string().trim().min(1, "Email is required.").email("Invalid email address."),
    password: z.string().min(1, "Password is required.")
});

function ChangeEmailForm({ onSubmit, loading }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        resolver: zodResolver(changeEmailSchema),
        defaultValues: {
            newEmail: "",
            password: ""
        }
    });

    function submit(data) {
        onSubmit(data);
        reset();
    }

    return (
        <form onSubmit={handleSubmit(submit)}>
            <TextField fullWidth label="New Email" type="email" {...register("newEmail")} error={!!errors.newEmail} helperText={errors.newEmail?.message} sx={{ mb: 2 }} />

            <TextField fullWidth label="Current Password" type="password" {...register("password")} error={!!errors.password} helperText={errors.password?.message} sx={{ mb: 2 }} />

            <Button type="submit" variant="contained" disabled={loading} sx={{ textTransform: "none" }}>
                {loading ? "Changing..." : "Change Email"}
            </Button>
        </form>
    );
}

export default ChangeEmailForm;