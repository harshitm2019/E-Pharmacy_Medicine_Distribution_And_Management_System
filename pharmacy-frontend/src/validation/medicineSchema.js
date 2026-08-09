import { z } from "zod";

export const medicineSchema = z.object({
    medicineName: z.string().trim()
        .min(3, "Medicine name must be between 3 and 100 characters.")
        .max(100, "Medicine name must not exceed 100 characters."),

    categoryId: z.coerce.number().int().positive("Category is required."),

    manufacturer: z.string().trim()
        .min(1, "Manufacturer is required.")
        .max(100, "Manufacturer must not exceed 100 characters."),

    manufactureDate: z.string()
        .min(1, "Manufacture date is required.")
        .refine(val => val <= new Date().toISOString().split("T")[0], "Manufacture date cannot be in the future."),

    expiryDate: z.string()
        .min(1, "Expiry date is required.")
        .refine(val => val > new Date().toISOString().split("T")[0], "Expiry date must be in the future."),

    batchNumber: z.string().trim()
        .min(1, "Batch number is required.")
        .max(100, "Batch number must not exceed 100 characters."),

    price: z.coerce.number().positive("Price must be greater than 0."),
    discount: z.coerce.number().min(0, "Discount cannot be negative.").max(100, "Discount cannot exceed 100%."),
    stockQuantity: z.coerce.number().int().min(0, "Stock quantity cannot be negative."),
    
    description: z.string().trim()
        .min(1, "Description is required.")
        .max(300, "Description must not exceed 300 characters."),

    prescriptionNeed: z.enum(["YES", "NO"], { message: "Prescription requirement is required." }),
    medicineImage: z.string().url("Enter a valid image URL.").optional().or(z.literal(""))
});
