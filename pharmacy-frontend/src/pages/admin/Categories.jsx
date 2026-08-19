import { Box, Button, TextField, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import CategoryDialog from "../../components/admin/category/CategoryDialog";
import CategoryForm from "../../components/admin/category/CategoryForm";
import CategoryTable from "../../components/admin/category/CategoryTable";
import DeleteCategoryDialog from "../../components/admin/category/DeleteCategoryDialog";
import useCategories from "../../hooks/useCategories";
import useCreateCategory from "../../hooks/admin/useCreateCategory";
import useDeleteCategory from "../../hooks/admin/useDeleteCategory";
import useSearchCategories from "../../hooks/useSearchCategories";
import useUpdateCategory from "../../hooks/admin/useUpdateCategory";

function Categories() {
    const [openDialog, setOpenDialog] = useState(false);
    const [editCategory, setEditCategory] = useState(null);
    const [deleteCategory, setDeleteCategory] = useState(null);
    const [keyword, setKeyword] = useState("");

    const { data: allData, isLoading: allLoading } = useCategories();
    const { data: searchData, isLoading: searchLoading } = useSearchCategories(keyword);
    const createCategory = useCreateCategory();
    const updateCategory = useUpdateCategory();
    const deleteCategoryMutation = useDeleteCategory();

    const categories = keyword.trim() ? searchData?.data ?? [] : allData?.data ?? [];
    const isLoading = keyword.trim() ? searchLoading : allLoading;

    function handleCreate(data) {
        createCategory.mutate(data, {
            onSuccess: response => {
                toast.success(response.message);
                setOpenDialog(false);
            },
            onError: error => toast.error(error.response?.data?.message || "Failed to create category.")
        });
    }

    function handleEdit(category) {
        setEditCategory(category);
        setOpenDialog(true);
    }

    function handleUpdate(data) {
        updateCategory.mutate(
            { categoryId: editCategory.categoryId, data },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setOpenDialog(false);
                    setEditCategory(null);
                },
                onError: error => toast.error(error.response?.data?.message || "Failed to update category.")
            }
        );
    }

    function handleDelete(category) {
        setDeleteCategory(category);
    }

    function confirmDelete() {
        deleteCategoryMutation.mutate(deleteCategory.categoryId, {
            onSuccess: response => {
                toast.success(response.message);
                setDeleteCategory(null);
            },
            onError: error => toast.error(error.response?.data?.message || "Failed to delete category.")
        });
    }

    function handleCloseDialog() {
        setOpenDialog(false);
        setEditCategory(null);
    }

    return (
        <Box>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 3 }}>
                <Typography variant="h4" fontWeight={700}>Categories</Typography>
                <Button variant="contained" onClick={() => setOpenDialog(true)}>Add Category</Button>
            </Box>

            <TextField
                fullWidth
                label="Search Categories"
                placeholder="Search by category name..."
                value={keyword}
                onChange={e => setKeyword(e.target.value)}
                sx={{ mb: 3 }}
            />

            <CategoryTable
                categories={categories}
                isLoading={isLoading}
                onEdit={handleEdit}
                onDelete={handleDelete}
            />

            <CategoryDialog
                open={openDialog}
                title={editCategory ? "Edit Category" : "Add Category"}
                onClose={handleCloseDialog}
                loading={editCategory ? updateCategory.isPending : createCategory.isPending}
            >
                <CategoryForm
                    category={editCategory}
                    onSubmit={editCategory ? handleUpdate : handleCreate}
                />
            </CategoryDialog>

            <DeleteCategoryDialog
                open={!!deleteCategory}
                category={deleteCategory}
                onClose={() => setDeleteCategory(null)}
                onConfirm={confirmDelete}
                loading={deleteCategoryMutation.isPending}
            />
        </Box>
    );
}

export default Categories;