import { Box, Divider, Paper, Stack, Typography } from "@mui/material";
import toast from "react-hot-toast";

import ChangeEmailForm from "../components/settings/ChangeEmailForm";
import ChangePasswordForm from "../components/settings/ChangePasswordForm";
import ProfileForm from "../components/settings/ProfileForm";

import useChangeEmail from "../hooks/user/useChangeEmail";
import useChangePassword from "../hooks/user/useChangePassword";
import useMyProfile from "../hooks/user/useMyProfile";
import useUpdateProfile from "../hooks/user/useUpdateProfile";

function Settings() {
    const { data, isLoading } = useMyProfile();

    const updateProfileMutation = useUpdateProfile();
    const changeEmailMutation = useChangeEmail();
    const changePasswordMutation = useChangePassword();

    const profile = data?.data;

    function handleUpdateProfile(formData) {
        updateProfileMutation.mutate(formData, {
            onSuccess: response => {
                toast.success(response.message);
            },
            onError: error => {
                toast.error(error.response?.data?.message || "Failed to update profile.");
            }
        });
    }

    function handleChangeEmail(formData) {
        changeEmailMutation.mutate(formData, {
            onSuccess: response => {
                toast.success(response.message);
            },
            onError: error => {
                toast.error(error.response?.data?.message || "Failed to change email.");
            }
        });
    }

    function handleChangePassword(formData) {
        changePasswordMutation.mutate(formData, {
            onSuccess: response => {
                toast.success(response.message);
            },
            onError: error => {
                toast.error(error.response?.data?.message || "Failed to change password.");
            }
        });
    }

    if (isLoading) {
        return <Typography>Loading settings...</Typography>;
    }

    return (
        <Box sx={{ width: "100%", maxWidth: 1000, mx: "auto" }}>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>
                Settings
            </Typography>

            <Typography color="text.secondary" sx={{ mb: 4 }}>
                Manage your profile and account security.
            </Typography>

            <Stack spacing={3}>
                <Paper elevation={0} sx={{ p: { xs: 2, sm: 3, md: 4 }, border: "1px solid #E5E7EB", borderRadius: 3 }}>
                    <Typography variant="h6" fontWeight={700} sx={{ mb: 1 }}>
                        Profile
                    </Typography>

                    <Typography color="text.secondary" sx={{ mb: 3 }}>
                        Update your personal information.
                    </Typography>

                    <ProfileForm
                        profile={profile}
                        onSubmit={handleUpdateProfile}
                        loading={updateProfileMutation.isPending}
                    />
                </Paper>

                <Paper elevation={0} sx={{ p: { xs: 2, sm: 3, md: 4 }, border: "1px solid #E5E7EB", borderRadius: 3 }}>
                    <Typography variant="h6" fontWeight={700}>
                        Account Security
                    </Typography>

                    <Typography color="text.secondary" sx={{ mt: 1, mb: 3 }}>
                        Change your email address.
                    </Typography>

                    <ChangeEmailForm
                        onSubmit={handleChangeEmail}
                        loading={changeEmailMutation.isPending}
                    />

                    <Divider sx={{ my: 4 }} />

                    <Typography variant="h6" fontWeight={700}>
                        Change Password
                    </Typography>

                    <Typography color="text.secondary" sx={{ mt: 1, mb: 3 }}>
                        Keep your account secure by using a strong password.
                    </Typography>

                    <ChangePasswordForm
                        onSubmit={handleChangePassword}
                        loading={changePasswordMutation.isPending}
                    />
                </Paper>
            </Stack>
        </Box>
    );
}
export default Settings;