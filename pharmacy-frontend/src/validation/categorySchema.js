import { z } from "zod";

export const categorySchema = z.object({
    categoryName: z.string().trim().min(3, "Category name must be between 3 and 100 characters.").max(100, "Category name must not exceed 100 characters."),
    description: z.string().trim().min(1, "Description is required.").max(500, "Description cannot exceed 500 characters.")
});