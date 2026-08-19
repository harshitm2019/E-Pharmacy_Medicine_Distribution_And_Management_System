import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import { Box, Button, FormControl, InputLabel, MenuItem, Select, Stack, TextField, ToggleButton, ToggleButtonGroup, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

import MedicineGrid from "../../components/customer/medicine/MedicineGrid";
import useMedicinesByCategory from "../../hooks/customer/useMedicinesByCategory";
import useSearchMedicines from "../../hooks/customer/useSearchMedicines";
import useCategories from "../../hooks/useCategories";
import useCart from "../../hooks/customer/useCart";


function Medicines() {
    const [mode, setMode] = useState("category");
    const [categoryId, setCategoryId] = useState("");
    const [searchInput, setSearchInput] = useState("");
    const [searchKeyword, setSearchKeyword] = useState("");
    const [page, setPage] = useState(0);
    const [size] = useState(12);
    const { addToCart } = useCart();

    const { data: categoriesData, isLoading: categoriesLoading } = useCategories();

    const { data: categoryData, isLoading: categoryLoading } = useMedicinesByCategory({ categoryId, page, size });

    const { data: searchData, isLoading: searchLoading } = useSearchMedicines({ keyword: searchKeyword, page, size });

    const categories = categoriesData?.data ?? [];

    const medicineData = mode === "category" ? categoryData : searchData;
    const isLoading = mode === "category" ? categoryLoading : searchLoading;

    useEffect(() => {
        setPage(0);
    }, [mode, categoryId, searchKeyword]);

    function handleModeChange(_, newMode) {
        if (!newMode) return;
        setMode(newMode);
        setCategoryId("");
        setSearchInput("");
        setSearchKeyword("");
        setPage(0);
    }

    function handleCategoryChange(event) {
        setCategoryId(event.target.value);
        setPage(0);
    }

    function handleSearch() {
        const keyword = searchInput.trim();

        if (!keyword) {
            toast.error("Please enter a medicine name.");
            return;
        }

        setSearchKeyword(keyword);
        setPage(0);
    }

    function handleAddToCart(medicine, quantity = 1) {
    if (medicine.stockQuantity <= 0) {
        toast.error("Medicine is out of stock.");
        return;
    }

    addToCart(medicine, quantity);

    toast.success(
        `${medicine.medicineName} added to cart.`
    );
}

    return (
        <Box>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>
                Medicines
            </Typography>

            <Typography color="text.secondary" sx={{ mb: 4 }}>
                Find medicines by category or search by medicine name.
            </Typography>

            <ToggleButtonGroup value={mode} exclusive onChange={handleModeChange} sx={{ mb: 3 ,mr:4 }}>
                <ToggleButton value="category">Search by Category</ToggleButton>
                <ToggleButton value="search">Search Medicine</ToggleButton>
            </ToggleButtonGroup>

            {mode === "category" && (
                <FormControl size="small" sx={{ minWidth: 280, mb: 4 }}>
                    <InputLabel>Category</InputLabel>

                    <Select value={categoryId} label="Category" onChange={handleCategoryChange} disabled={categoriesLoading}>
                        <MenuItem value="">Select Category</MenuItem>

                        {categories.map(category => (
                            <MenuItem key={category.categoryId} value={category.categoryId}>
                                {category.categoryName}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            )}

            {mode === "search" && (
                <Stack direction={{ xs: "column", sm: "row" }} spacing={2} sx={{ mb: 4 }}>
                    <TextField fullWidth size="small" label="Medicine Name" placeholder="Enter medicine name" value={searchInput} onChange={event => setSearchInput(event.target.value)} onKeyDown={event => { if (event.key === "Enter") handleSearch(); }} />

                    <Button variant="contained" startIcon={<SearchOutlinedIcon />} onClick={handleSearch} disabled={!searchInput.trim()}>
                        Search
                    </Button>
                </Stack>
            )}

            {((mode === "category" && !categoryId) || (mode === "search" && !searchKeyword)) ? (
                <Box sx={{ py: 8, textAlign: "center" }}>
                    <Typography color="text.secondary">
                        {mode === "category" ? "Please select a category to view medicines." : "Enter a medicine name to search."}
                    </Typography>
                </Box>
            ) : (
                <MedicineGrid data={medicineData} isLoading={isLoading} onAddToCart={handleAddToCart} />
            )}
        </Box>
    );
}

export default Medicines;