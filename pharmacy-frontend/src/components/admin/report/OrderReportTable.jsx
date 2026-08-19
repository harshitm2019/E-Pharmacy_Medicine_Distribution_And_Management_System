import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function OrderReportTable({ data, isLoading, page, size, setPage, setSize }) {
    const orders = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) return <Typography>Loading order report...</Typography>;

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Customer Name</TableCell>
                            <TableCell>Order Date</TableCell>
                            <TableCell>Total Amount</TableCell>
                            <TableCell>Status</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {orders.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No orders found for the selected dates</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            orders.map(order => (
                                <TableRow key={order.orderId} hover>
                                    <TableCell>{order.orderId}</TableCell>
                                    <TableCell>{order.customerName}</TableCell>
                                    <TableCell>{order.orderDate}</TableCell>
                                    <TableCell>₹{order.totalAmount}</TableCell>
                                    <TableCell>{order.status}</TableCell>
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

export default OrderReportTable;