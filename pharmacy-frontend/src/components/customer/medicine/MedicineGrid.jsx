import { Grid, Typography } from "@mui/material";
import MedicineCard from "./MedicineCard";

function MedicineGrid({ data, isLoading, onAddToCart }) {
    if (isLoading) {
        return <Typography>Loading medicines...</Typography>;
    }

    const medicines = data?.data?.content ?? [];

    if (medicines.length === 0) {
        return <Typography color="text.secondary" sx={{ py: 5, textAlign: "center" }}>No medicines found.</Typography>;
    }

    return (
        <Grid container spacing={3}>
            {medicines.map(medicine => (
                <Grid key={medicine.medicineId} size={{ xs: 12, sm: 6, md: 4, lg: 3 }}>
                    <MedicineCard medicine={medicine} onAddToCart={onAddToCart} />
                </Grid>
            ))}
        </Grid>
    );
}
export default MedicineGrid;