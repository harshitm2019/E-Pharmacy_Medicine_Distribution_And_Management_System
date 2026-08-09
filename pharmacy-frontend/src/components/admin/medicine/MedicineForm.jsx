import { zodResolver } from "@hookform/resolvers/zod";
import { Grid, MenuItem, TextField } from "@mui/material";
import { useEffect } from "react";
import { Controller, useForm } from "react-hook-form";

import useCategories from "../../../hooks/useCategories";
import { medicineSchema } from "../../../validation/medicineSchema";

function MedicineForm({ onSubmit, medicine = null }) {

    const { data } = useCategories();
    const categories = data?.data ?? [];

    const { register, handleSubmit, reset, control, formState: { errors } } = useForm({
        resolver: zodResolver(medicineSchema),
        defaultValues: {
            medicineName: "",
            categoryId: "",
            manufacturer: "",
            manufactureDate: "",
            expiryDate: "",
            batchNumber: "",
            price: "",
            discount: "",
            stockQuantity: "",
            description: "",
            prescriptionNeed: "",
            medicineImage: ""
        }
    });

    useEffect(() => {
        if (medicine && categories.length > 0) {
            reset({
                medicineName: medicine.medicineName ?? "",
                categoryId: String(medicine.categoryId ?? ""),
                manufacturer: medicine.manufacturer ?? "",
                manufactureDate: medicine.manufactureDate ?? "",
                expiryDate: medicine.expiryDate ?? "",
                batchNumber: medicine.batchNumber ?? "",
                price: medicine.price ?? "",
                discount: medicine.discount ?? "",
                stockQuantity: medicine.stockQuantity ?? "",
                description: medicine.description ?? "",
                prescriptionNeed: String(medicine.prescriptionNeed ?? ""),
                medicineImage: medicine.medicineImage ?? ""
            });
        } else if (!medicine) {
            reset({
                medicineName: "",
                categoryId: "",
                manufacturer: "",
                manufactureDate: "",
                expiryDate: "",
                batchNumber: "",
                price: "",
                discount: "",
                stockQuantity: "",
                description: "",
                prescriptionNeed: "",
                medicineImage: ""
            });
        }
    }, [medicine, categories, reset]);

    return (
        <form id="medicine-form" onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={2} sx={{ pt: 1 }}>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Medicine Name" {...register("medicineName")} error={!!errors.medicineName} helperText={errors.medicineName?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <Controller
                        name="categoryId"
                        control={control}
                        render={({ field }) => (
                            <TextField
                                select
                                fullWidth
                                label="Category"
                                {...field}
                                error={!!errors.categoryId}
                                helperText={errors.categoryId?.message}
                            >
                                {categories.map((category) => (
                                    <MenuItem key={category.categoryId} value={String(category.categoryId)}>
                                        {category.categoryName}
                                    </MenuItem>
                                ))}
                            </TextField>
                        )}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Manufacturer" {...register("manufacturer")} error={!!errors.manufacturer} helperText={errors.manufacturer?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Batch Number" {...register("batchNumber")} error={!!errors.batchNumber} helperText={errors.batchNumber?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth type="date" label="Manufacture Date" slotProps={{ inputLabel: { shrink: true } }} {...register("manufactureDate")} error={!!errors.manufactureDate} helperText={errors.manufactureDate?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth type="date" label="Expiry Date" slotProps={{ inputLabel: { shrink: true } }} {...register("expiryDate")} error={!!errors.expiryDate} helperText={errors.expiryDate?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 4 }}>
                    <TextField fullWidth type="number" label="Price" {...register("price")} error={!!errors.price} helperText={errors.price?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 4 }}>
                    <TextField fullWidth type="number" label="Discount (%)" {...register("discount")} error={!!errors.discount} helperText={errors.discount?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 4 }}>
                    <TextField fullWidth type="number" label="Stock Quantity" {...register("stockQuantity")} error={!!errors.stockQuantity} helperText={errors.stockQuantity?.message} />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <Controller
                        name="prescriptionNeed"
                        control={control}
                        render={({ field }) => (
                            <TextField
                                select
                                fullWidth
                                label="Prescription Required"
                                {...field}
                                error={!!errors.prescriptionNeed}
                                helperText={errors.prescriptionNeed?.message}
                            >
                                <MenuItem value="">Select Requirement</MenuItem>
                                <MenuItem value="YES">Yes</MenuItem>
                                <MenuItem value="NO">No</MenuItem>
                            </TextField>
                        )}
                    />
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                    <TextField fullWidth label="Medicine Image URL" placeholder="https://example.com/image.jpg" {...register("medicineImage")} error={!!errors.medicineImage} helperText={errors.medicineImage?.message} />
                </Grid>

                <Grid size={12}>
                    <TextField fullWidth multiline rows={4} label="Description" {...register("description")} error={!!errors.description} helperText={errors.description?.message} />
                </Grid>

            </Grid>
        </form>
    );
}
export default MedicineForm;