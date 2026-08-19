import { zodResolver } from "@hookform/resolvers/zod";
import { Button, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import { z } from "zod";

const changePasswordSchema = z.object({
    oldPassword: z.string().min(1, "Old password is required."),
    newPassword: z.string().min(8, "Password must be at least 8 characters.").max(20, "Password must not exceed 20 characters."),
    confirmPassword: z.string().min(1, "Please confirm your new password.")
}).refine(data => data.newPassword === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Passwords do not match."
});

function ChangePasswordForm({ onSubmit, loading }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        resolver: zodResolver(changePasswordSchema),
        defaultValues: {
            oldPassword: "",
            newPassword: "",
            confirmPassword: ""
        }
    });

    function submit(data) {
        onSubmit(data);
        reset();
    }

    return (
        <form onSubmit={handleSubmit(submit)}>
            <TextField
                fullWidth
                label="Old Password"
                type="password"
                {...register("oldPassword")}
                error={!!errors.oldPassword}
                helperText={errors.oldPassword?.message}
                sx={{ mb: 2 }}
            />

            <TextField
                fullWidth
                label="New Password"
                type="password"
                {...register("newPassword")}
                error={!!errors.newPassword}
                helperText={errors.newPassword?.message}
                sx={{ mb: 2 }}
            />

            <TextField
                fullWidth
                label="Confirm New Password"
                type="password"
                {...register("confirmPassword")}
                error={!!errors.confirmPassword}
                helperText={errors.confirmPassword?.message}
                sx={{ mb: 2 }}
            />

            <Button type="submit" variant="contained" disabled={loading} sx={{ textTransform: "none" }}>
                {loading ? "Changing..." : "Change Password"}
            </Button>
        </form>
    );
}

export default ChangePasswordForm;