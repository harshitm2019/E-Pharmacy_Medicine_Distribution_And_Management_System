import { zodResolver } from "@hookform/resolvers/zod";
import { Box, TextField } from "@mui/material";
import { useEffect } from "react";
import { useForm } from "react-hook-form";

import { categorySchema } from "../../../validation/categorySchema";

function CategoryForm({ onSubmit, category = null }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        resolver: zodResolver(categorySchema),
        defaultValues: { categoryName: "", description: "" }
    });

    useEffect(() => {
        reset({
            categoryName: category?.categoryName ?? "",
            description: category?.description ?? ""
        });
    }, [category, reset]);

    return (
        <form id="category-form" onSubmit={handleSubmit(onSubmit)}>
            <Box sx={{ pt: 1 }}>
                <TextField fullWidth label="Category Name" {...register("categoryName")} error={!!errors.categoryName} helperText={errors.categoryName?.message} sx={{ mb: 2 }} />
                <TextField fullWidth multiline rows={4} label="Description" {...register("description")} error={!!errors.description} helperText={errors.description?.message} />
            </Box>
        </form>
    );
}

export default CategoryForm;