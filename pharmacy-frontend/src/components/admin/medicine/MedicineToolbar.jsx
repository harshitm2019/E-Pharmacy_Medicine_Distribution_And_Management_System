import AddIcon from "@mui/icons-material/Add";
import SearchIcon from "@mui/icons-material/Search";
import { Box, Button, InputAdornment, MenuItem, Stack, TextField } from "@mui/material";
import useCategories from "../../../hooks/useCategories";



function MedicineToolbar({ keyword, setKeyword, categoryId, setCategoryId, status, setStatus, onAdd }) {

    const { data } = useCategories();
    const categories = data?.data ?? [];

    return (
        <Stack
            direction={{ xs: "column", lg: "row" }}
            spacing={2}
            justifyContent="space-between"
            alignItems={{ xs: "stretch", lg: "center" }}
            mb={3}
        >
            <Stack direction={{ xs: "column", md: "row" }} spacing={2} flex={1}>
                <TextField
                    placeholder="Search medicine..."
                    value={keyword}
                    onChange={(event) => setKeyword(event.target.value)}
                    sx={{ minWidth: 300 }}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon />
                            </InputAdornment>
                        )
                    }}
                />

                <TextField
                    select
                    label="Category"
                    value={categoryId}
                    onChange={(event) => setCategoryId(event.target.value)}
                    sx={{ minWidth: 180 }}
                >
                    <MenuItem value="">All Categories</MenuItem>
                    {categories.map(category => (
                        <MenuItem key={category.categoryId} value={category.categoryId}>
                            {category.categoryName}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    select
                    label="Status"
                    value={status}
                    onChange={(event) => setStatus(event.target.value)}
                    sx={{ minWidth: 180 }}
                >
                    <MenuItem value="">All Status</MenuItem>
                    <MenuItem value="ACTIVE">Active</MenuItem>
                    <MenuItem value="INACTIVE">Inactive</MenuItem>
                </TextField>
            </Stack>

            <Box>
                <Button variant="contained" startIcon={<AddIcon />} onClick={onAdd} 
                 sx={{ borderRadius: "12px", px: 3, py: 1.2, textTransform: "none", fontWeight: 700 }}>
                    Add Medicine
                </Button>
            </Box>
        </Stack>
    );
}
export default MedicineToolbar;
