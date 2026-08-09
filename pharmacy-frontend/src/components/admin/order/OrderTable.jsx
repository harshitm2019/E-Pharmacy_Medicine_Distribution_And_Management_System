import VisibilityIcon from "@mui/icons-material/Visibility";
import { IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Tooltip, Typography } from "@mui/material";

import OrderActions from "./OrderActions";

function OrderTable({ orders, isLoading, page, size, pageData, setPage, setSize, onView, onOrderStatus, onPrescriptionStatus, onCancel, onPrescriptionView }) {
    if (isLoading) {
        return <Typography>Loading orders...</Typography>;
    }

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Order Date</TableCell>
                            <TableCell>Total Amount</TableCell>
                            <TableCell>Payment</TableCell>
                            <TableCell>Order Status</TableCell>
                            <TableCell>Prescription</TableCell>
                            <TableCell align="center">Actions</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {orders.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={7} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No orders found</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            orders.map(order => (
                                <TableRow key={order.orderId} hover>
                                    <TableCell>
                                        <Typography fontWeight={600}>#{order.orderId}</Typography>
                                    </TableCell>

                                    <TableCell>{order.orderDate}</TableCell>

                                    <TableCell>₹{order.totalAmount}</TableCell>

                                    <TableCell>{order.paymentStatus}</TableCell>

                                    <TableCell>{order.orderStatus}</TableCell>

                                    <TableCell>
                                        {order.prescription ? (
                                            <Tooltip title="View prescription details">
                                                <Typography
                                                    component="span"
                                                    sx={{ cursor: "pointer", color: "primary.main", fontWeight: 600 }}
                                                    onClick={() => onPrescriptionView(order.prescription)}
                                                >
                                                    {order.prescription.status}
                                                </Typography>
                                            </Tooltip>
                                        ) : (
                                            <Typography component="span" color="text.secondary">
                                                N/A
                                            </Typography>
                                        )}
                                    </TableCell>

                                    <TableCell align="center">
                                        <OrderActions
                                            order={order}
                                            onView={onView}
                                            onOrderStatus={onOrderStatus}
                                            onPrescriptionStatus={onPrescriptionStatus}
                                            onCancel={onCancel}
                                        />
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

export default OrderTable;