import VisibilityIcon from "@mui/icons-material/Visibility";
import EditOutlined from "@mui/icons-material/EditOutlined";
import { Box, Button, Checkbox, IconButton, Skeleton, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Paper, Typography } from "@mui/material";

function MedicineTable({ data, isLoading, page, size, setPage, setSize, onView, onEdit, selectedIds, onSelect, onSelectAll, onStatusChange, isUpdating }) {

    const medicines = data?.data?.content ?? [];
    const totalElements = data?.data?.page?.totalElements ?? 0;
    const currentPageIds = medicines.map(medicine => medicine.medicineId);
    const allSelected = medicines.length > 0 && medicines.every(medicine => selectedIds.includes(medicine.medicineId));

    function handleSelectAll() {
        onSelectAll(currentPageIds);
    }

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>

            {selectedIds.length > 0 && (
                <Stack direction="row" spacing={2} alignItems="center" sx={{ px: 2, py: 1.5, borderBottom: "1px solid #E5E7EB" }}>
                    <Typography fontWeight={600}>{selectedIds.length} selected</Typography>
                    <Button size="small" variant="contained" onClick={() => onStatusChange("ACTIVE")} disabled={isUpdating}>Activate</Button>
                    <Button size="small" variant="outlined" onClick={() => onStatusChange("INACTIVE")} disabled={isUpdating}>Deactivate</Button>
                </Stack>
            )}

            <TableContainer>
                <Table>

                    <TableHead>
                        <TableRow>
                            <TableCell padding="checkbox">
                                <Checkbox checked={allSelected} onChange={handleSelectAll} />
                            </TableCell>
                            <TableCell>Medicine</TableCell>
                            <TableCell>Category</TableCell>
                            <TableCell>Manufacturer</TableCell>
                            <TableCell>Price</TableCell>
                            <TableCell>Stock</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center">Actions</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>

                        {isLoading ? (
                            Array.from({ length: size }).map((_, index) => (
                                <TableRow key={index}>
                                    <TableCell><Skeleton variant="rectangular" width={24} height={24} /></TableCell>
                                    <TableCell><Skeleton width={150} /></TableCell>
                                    <TableCell><Skeleton width={100} /></TableCell>
                                    <TableCell><Skeleton width={120} /></TableCell>
                                    <TableCell><Skeleton width={70} /></TableCell>
                                    <TableCell><Skeleton width={60} /></TableCell>
                                    <TableCell><Skeleton width={80} /></TableCell>
                                    <TableCell><Skeleton width={40} /></TableCell>
                                </TableRow>
                            ))
                        ) : medicines.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={8} align="center" sx={{ py: 6 }}>
                                    No medicines found
                                </TableCell>
                            </TableRow>
                        ) : (
                            medicines.map(medicine => (
                                <TableRow key={medicine.medicineId} hover>

                                    <TableCell padding="checkbox">
                                        <Checkbox checked={selectedIds.includes(medicine.medicineId)} onChange={() => onSelect(medicine.medicineId)} />
                                    </TableCell>

                                    <TableCell>
                                        <Stack direction="row" spacing={1.5} alignItems="center">
                                            {medicine.medicineImage && <Box component="img" src={medicine.medicineImage} alt={medicine.medicineName} sx={{ width: 45, height: 45, objectFit: "contain", borderRadius: 1 }} />}
                                            <Typography fontWeight={600}>{medicine.medicineName}</Typography>
                                        </Stack>
                                    </TableCell>

                                    <TableCell>{medicine.categoryName}</TableCell>
                                    <TableCell>{medicine.manufacturer}</TableCell>
                                    <TableCell>₹{medicine.sellingPrice}</TableCell>
                                    <TableCell>{medicine.stockQuantity}</TableCell>
                                    <TableCell>{medicine.status}</TableCell>

                                    <TableCell align="center">
                                        <IconButton onClick={() => onView(medicine)} color="primary">
                                            <VisibilityIcon />
                                        </IconButton>
                                        <IconButton onClick={() => onEdit(medicine)} color="black">
                                            <EditOutlined />
                                        </IconButton>
                                    </TableCell>

                                </TableRow>
                            ))
                        )}

                    </TableBody>

                </Table>
            </TableContainer>

            <TablePagination
                component="div"
                count={totalElements}
                page={page}
                rowsPerPage={size}
                onPageChange={(_, newPage) => setPage(newPage)}
                onRowsPerPageChange={event => {
                    setSize(Number(event.target.value));
                    setPage(0);
                }}
                rowsPerPageOptions={[5, 10, 20]}
            />

        </Paper>
    );
}

export default MedicineTable;