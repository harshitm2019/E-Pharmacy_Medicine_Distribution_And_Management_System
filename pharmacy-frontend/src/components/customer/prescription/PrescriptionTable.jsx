import { Button, Chip, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function PrescriptionTable({ data, isLoading, page, size, setPage, setSize, onView, onReplace }) {
    const prescriptions = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) {
        return <Typography>Loading prescriptions...</Typography>;
    }

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Prescription ID</TableCell>
                            <TableCell>Doctor Name</TableCell>
                            <TableCell>Uploaded Date</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center">Action</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {prescriptions.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">
                                        No prescriptions found.
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            prescriptions.map(prescription => (
                                <TableRow key={prescription.prescriptionId} hover>
                                    <TableCell>
                                        {prescription.prescriptionId}
                                    </TableCell>

                                    <TableCell>
                                        {prescription.doctorName}
                                    </TableCell>

                                    <TableCell>
                                        {new Date(prescription.uploadedDate).toLocaleString()}
                                    </TableCell>

                                    <TableCell>
                                        <Chip
                                            label={prescription.status}
                                            size="small"
                                            color={
                                                prescription.status === "APPROVED"
                                                    ? "success"
                                                    : prescription.status === "REJECTED"
                                                        ? "error"
                                                        : "warning"
                                            }
                                        />
                                    </TableCell>

                                    <TableCell align="center">
                                        <Button
                                            size="small"
                                            variant="outlined"
                                            onClick={() => onView(prescription)}
                                            sx={{ mr: 1 }}
                                        >
                                            View
                                        </Button>

                                        <Button
                                            size="small"
                                            variant="contained"
                                            disabled={prescription.status === "APPROVED"}
                                            onClick={() => onReplace(prescription)}
                                        >
                                            Replace
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            {pageData && (
                <TablePagination
                    component="div"
                    count={pageData.totalElements}
                    page={pageData.number}
                    rowsPerPage={pageData.size}
                    onPageChange={(_, newPage) => setPage(newPage)}
                    onRowsPerPageChange={event => {
                        setSize(parseInt(event.target.value, 10));
                        setPage(0);
                    }}
                    rowsPerPageOptions={[5, 10, 20, 50]}
                />
            )}
        </Paper>
    );
}
export default PrescriptionTable;