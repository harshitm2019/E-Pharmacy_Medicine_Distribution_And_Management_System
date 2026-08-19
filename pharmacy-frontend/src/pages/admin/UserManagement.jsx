import { Box, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import CreateUserDialog from "../../components/admin/user/CreateUserDialog";
import UserManagementTable from "../../components/admin/user/UserManagementTable";
import UserManagementToolbar from "../../components/admin/user/UserManagementToolbar";
import useAdminUsers from "../../hooks/admin/useAdminUsers";
import useCreateUser from "../../hooks/admin/useCreateUser";
import useUpdateUserStatus from "../../hooks/admin/useUpdateUserStatus";

function UserManagement() {
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [role, setRole] = useState("");
    const [email, setEmail] = useState("");
    const [createDialogOpen, setCreateDialogOpen] = useState(false);

    const { data, isLoading } = useAdminUsers({
        page,
        size,
        role,
        email
    });

    const createUserMutation = useCreateUser();
    const updateUserStatusMutation = useUpdateUserStatus();

    function handleCreateUser(data) {
        createUserMutation.mutate(data, {
            onSuccess: response => {
                toast.success(response.message);
                setCreateDialogOpen(false);
            },
            onError: error => {
                toast.error(
                    error.response?.data?.message ||
                    "Failed to create user."
                );
            }
        });
    }

    function handleStatusChange(userId, status) {
        updateUserStatusMutation.mutate(
            { userId, status },
            {
                onSuccess: response => {
                    toast.success(response.message);
                },
                onError: error => {
                    toast.error(
                        error.response?.data?.message ||
                        "Failed to update user status."
                    );
                }
            }
        );
    }

    return (
        <Box>
            <Typography variant="h4" fontWeight={700}>
                User Management
            </Typography>

            <Box sx={{ mt: 2 }}>
                <UserManagementToolbar
                    role={role}
                    email={email}
                    setRole={setRole}
                    setEmail={setEmail}
                    setPage={setPage}
                    onCreate={() => setCreateDialogOpen(true)}
                />
            </Box>

            <Box sx={{ mt: 4 }}>
                <UserManagementTable
                    data={data}
                    isLoading={isLoading}
                    page={page}
                    size={size}
                    setPage={setPage}
                    setSize={setSize}
                    onStatusChange={handleStatusChange}
                />
            </Box>

            <CreateUserDialog
                open={createDialogOpen}
                onClose={() => setCreateDialogOpen(false)}
                onSubmit={handleCreateUser}
                loading={createUserMutation.isPending}
            />
        </Box>
    );
}
export default UserManagement;