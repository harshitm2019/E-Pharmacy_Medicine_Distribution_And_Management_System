import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { IconButton, Box, Paper, Skeleton, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";

function CategoryTable({ categories, isLoading, onEdit, onDelete }) {
    return (
        <TableContainer component={Paper} elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <Table>

                <TableHead>
                    <TableRow>
                        <TableCell>Category</TableCell>
                        <TableCell>Description</TableCell>
                        <TableCell>Created</TableCell>
                        <TableCell>Updated</TableCell>
                        <TableCell align="center">Actions</TableCell>
                    </TableRow>
                </TableHead>

                <TableBody>

                    {isLoading ? (
                        Array.from({ length: 5 }).map((_, index) => (
                            <TableRow key={index}>
                                <TableCell><Skeleton width={150} /></TableCell>
                                <TableCell><Skeleton width={250} /></TableCell>
                                <TableCell><Skeleton width={150} /></TableCell>
                                <TableCell><Skeleton width={150} /></TableCell>
                                <TableCell align="center"><Skeleton width={80} /></TableCell>
                            </TableRow>
                        ))
                    ) : categories.length === 0 ? (
                        <TableRow>
                            <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                <Typography color="text.secondary">No categories found</Typography>
                            </TableCell>
                        </TableRow>
                    ) : (
                        categories.map(category => (
                            <TableRow key={category.categoryId} hover>
                                <TableCell>
                                    <Typography fontWeight={600}>{category.categoryName}</Typography>
                                </TableCell>

                                <TableCell>{category.description}</TableCell>
                                <TableCell>{category.createdDate}</TableCell>
                                <TableCell>{category.updatedDate}</TableCell>

                                <TableCell align="center">
                                    <Box sx={{ display: "flex", justifyContent: "center", gap: 1 }}>
                                        <IconButton color="primary" onClick={() => onEdit(category)}>
                                            <EditIcon />
                                        </IconButton>

                                        <IconButton color="error" onClick={() => onDelete(category)}>
                                            <DeleteIcon />
                                        </IconButton>
                                    </Box>
                                </TableCell>
                            </TableRow>
                        ))
                    )}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
export default CategoryTable;