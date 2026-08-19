import AddIcon from "@mui/icons-material/Add";
import SearchIcon from "@mui/icons-material/Search";
import {Box, Button, MenuItem, TextField } from "@mui/material";

function UserManagementToolbar({ role, email, setRole, setEmail, setPage, onCreate }) {
    const handleRoleChange = event => {
        setRole(event.target.value);
        setPage(0);
    };

    const handleEmailChange = event => {
        setEmail(event.target.value);
        setPage(0);
    };

    return (
        <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
            <TextField select size="small" label="Role" value={role} onChange={handleRoleChange} sx={{ width: 180 }}>
                <MenuItem value="">All</MenuItem>
                <MenuItem value="ADMIN">Admin</MenuItem>
                <MenuItem value="CUSTOMER">Customer</MenuItem>
                <MenuItem value="DELIVERY_BOY">Delivery Boy</MenuItem>
            </TextField>

            <TextField
                size="small"
                label="Search by email"
                value={email}
                onChange={handleEmailChange}
                sx={{ width: 300 }}
                slotProps={{
                    input: {
                        startAdornment: <SearchIcon sx={{ mr: 1 }} />
                    }
                }}
            />
            <Button variant="contained" startIcon={<AddIcon />} onClick={onCreate}>
                Create User
            </Button>
        </Box>
    );
}
export default UserManagementToolbar;