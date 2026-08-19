import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function ReturnTable({ data, isLoading, page, size, setPage, setSize, onUpdate,onViewOrder }) {
    const returns = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) return <Typography>Loading returns...</Typography>;

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Return ID</TableCell>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Return Reason</TableCell>
                            <TableCell>Return Date</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center" sx={{ pr: 4 }}>Action</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {returns.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={6} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No returns found</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            returns.map(returnOrder => (
                                <TableRow key={returnOrder.returnId} hover>
                                    <TableCell>{returnOrder.returnId}</TableCell>
                                    <TableCell>{returnOrder.orderId}</TableCell>
                                    <TableCell>{returnOrder.returnReason}</TableCell>
                                    <TableCell>{returnOrder.returnDate}</TableCell>
                                    <TableCell>{returnOrder.returnStatus}</TableCell>
                                    <TableCell align="center" sx={{ pr: 4 }}>
                                        <Button size="small" variant="outlined" onClick={() => onViewOrder(returnOrder.orderId)} sx={{ mr: 1 }}>
                                            View Order
                                        </Button>
                                        {returnOrder.returnStatus !== "REJECTED" && returnOrder.returnStatus !== "REFUNDED" && (
                                            <Button size="small" variant="outlined" onClick={() => onUpdate(returnOrder)}>
                                                Update Status
                                            </Button>
                                        )}
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
export default ReturnTable;