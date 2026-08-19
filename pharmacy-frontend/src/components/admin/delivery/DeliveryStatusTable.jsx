import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function DeliveryStatusTable({ data, isLoading, page, size, setPage, setSize, onView }) {
    const deliveries = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) return <Typography>Loading deliveries...</Typography>;

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Delivery Boy</TableCell>
                            <TableCell>Vehicle Number</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center" sx={{ pr: 4 }}>Action</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {deliveries.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No deliveries found</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            deliveries.map(delivery => (
                                <TableRow key={delivery.orderId} hover>
                                    <TableCell>{delivery.orderId}</TableCell>
                                    <TableCell>{delivery.deliveryBoyName}</TableCell>
                                    <TableCell>{delivery.vehicleNo}</TableCell>
                                    <TableCell>{delivery.deliveryStatus}</TableCell>
                                    <TableCell align="center" sx={{ pr: 4 }}>
                                        <Button size="small" variant="outlined" onClick={() => onView(delivery)}>
                                            View Details
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
export default DeliveryStatusTable;