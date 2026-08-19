import { Box, Chip, CircularProgress, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import useMyPayments from "../../hooks/customer/useMyPayments";

function getPaymentStatusColor(status) {
    switch (status) {
        case "SUCCESS": return "success";
        case "PENDING": return "warning";
        case "FAILED": return "error";
        default: return "default";
    }
}

function MyPayments() {
    const { data, isLoading, isError } = useMyPayments();
    const payments = data?.data ?? [];

    if (isLoading) return <Box sx={{ display: "flex", justifyContent: "center", py: 10 }}><CircularProgress /></Box>;
    if (isError) return <Box sx={{ textAlign: "center", py: 8 }}><Typography color="error" fontWeight={600}>Unable to load payment records.</Typography></Box>;

    return (
        <Box>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>My Payments</Typography>
            <Typography color="text.secondary" sx={{ mb: 4 }}>View all your payment records.</Typography>

            {payments.length === 0 ? (
                <Box sx={{ textAlign: "center", py: 8 }}>
                    <Typography variant="h6" fontWeight={700}>No Payment Records</Typography>
                    <Typography color="text.secondary" sx={{ mt: 1 }}>Your payment records will appear here.</Typography>
                </Box>
            ) : (
                <TableContainer component={Paper} elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 2 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>Payment ID</strong></TableCell>
                                <TableCell><strong>Order ID</strong></TableCell>
                                <TableCell><strong>Payment Method</strong></TableCell>
                                <TableCell><strong>Amount</strong></TableCell>
                                <TableCell><strong>Status</strong></TableCell>
                                <TableCell><strong>Payment Date</strong></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {payments.map(payment => (
                                <TableRow key={payment.paymentId}>
                                    <TableCell>#{payment.paymentId}</TableCell>
                                    <TableCell>#{payment.orderId}</TableCell>
                                    <TableCell>{payment.paymentMethod || "—"}</TableCell>
                                    <TableCell>₹{Number(payment.amount ?? 0).toFixed(2)}</TableCell>
                                    <TableCell><Chip label={payment.paymentStatus} size="small" color={getPaymentStatusColor(payment.paymentStatus)} /></TableCell>
                                    <TableCell>{payment.paidDate ? new Date(payment.paidDate).toLocaleString() : "—"}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}
        </Box>
    );
}
export default MyPayments;