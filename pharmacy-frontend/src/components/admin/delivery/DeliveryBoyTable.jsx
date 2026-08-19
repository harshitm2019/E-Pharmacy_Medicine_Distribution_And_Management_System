import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function DeliveryBoyTable({ data, isLoading, page, size, setPage, setSize, onEdit, onAddInformation }) {
    const deliveryBoys = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) return <Typography>Loading delivery boys...</Typography>;

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>User Name</TableCell>
                            <TableCell>Vehicle Number</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center">Action</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {deliveryBoys.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No delivery boys found</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            deliveryBoys.map(deliveryBoy => (
                                <TableRow key={deliveryBoy.deliveryBoyId} hover>
                                    <TableCell>{deliveryBoy.deliveryBoyId}</TableCell>
                                    <TableCell>{deliveryBoy.username}</TableCell>
                                    <TableCell>{deliveryBoy.vehicleNo}</TableCell>
                                    <TableCell>{deliveryBoy.status}</TableCell>
                                    <TableCell align="center">
                                        <Button size="small"
                                         variant="outlined" onClick={() => onEdit(deliveryBoy)}>Edit</Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            {pageData && <TablePagination component="div" count={pageData.totalElements} page={pageData.number} rowsPerPage={pageData.size}
             onPageChange={(_, newPage) => setPage(newPage)} onRowsPerPageChange={event => { setSize(parseInt(event.target.value, 10)); setPage(0); }}
             rowsPerPageOptions={[5, 10, 20, 50]} />}
        </Paper>
    );
}

export default DeliveryBoyTable;