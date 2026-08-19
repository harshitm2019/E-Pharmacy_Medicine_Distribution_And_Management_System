import {
    Box,
    Button,
    CircularProgress,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";

import useAllPayments from "../../hooks/admin/useAllPayments";

function PaymentReport() {

    const {
        data,
        isLoading,
        refetch,
        isFetched
    } = useAllPayments();

    const payments = data?.data ?? [];

    function formatAmount(amount) {
        return Number(amount ?? 0).toFixed(2);
    }

    function formatDate(date) {
        if (!date) {
            return "-";
        }

        return new Date(date).toLocaleString();
    }

    return (
        <Box>

            <Box
                sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    mb: 3,
                    gap: 2
                }}
            >

                <Box>
                    <Typography
                        variant="h6"
                        fontWeight={700}
                    >
                        Payment Report
                    </Typography>

                    <Typography
                        variant="body2"
                        color="text.secondary"
                    >
                        View all payment records.
                    </Typography>
                </Box>

                <Button
                    variant="contained"
                    onClick={() => refetch()}
                    disabled={isLoading}
                >
                    {isLoading
                        ? "Loading..."
                        : "Load Payments"}
                </Button>

            </Box>

            {!isFetched ? (

                <Box
                    sx={{
                        py: 8,
                        textAlign: "center"
                    }}
                >
                    <Typography color="text.secondary">
                        Click "Load Payments" to view
                        payment records.
                    </Typography>
                </Box>

            ) : isLoading ? (

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        py: 8
                    }}
                >
                    <CircularProgress />
                </Box>

            ) : payments.length === 0 ? (

                <Box
                    sx={{
                        py: 8,
                        textAlign: "center"
                    }}
                >
                    <Typography color="text.secondary">
                        No payment records found.
                    </Typography>
                </Box>

            ) : (

                <TableContainer
                    component={Paper}
                    elevation={0}
                    sx={{
                        border:
                            "1px solid #E5E7EB",
                        borderRadius: 2,
                        overflowX: "auto"
                    }}
                >

                    <Table>

                        <TableHead>

                            <TableRow
                                sx={{
                                    bgcolor: "#F5F7FA"
                                }}
                            >

                                <TableCell
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Payment ID
                                </TableCell>

                                <TableCell
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Order ID
                                </TableCell>

                                <TableCell
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Payment Method
                                </TableCell>

                                <TableCell
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Payment Status
                                </TableCell>

                                <TableCell
                                    align="right"
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Amount
                                </TableCell>

                                <TableCell
                                    sx={{
                                        fontWeight: 700
                                    }}
                                >
                                    Paid Date
                                </TableCell>

                            </TableRow>

                        </TableHead>

                        <TableBody>

                            {payments.map(payment => (

                                <TableRow
                                    key={
                                        payment.paymentId
                                    }
                                    hover
                                >

                                    <TableCell>
                                        {payment.paymentId}
                                    </TableCell>

                                    <TableCell>
                                        #{payment.orderId}
                                    </TableCell>

                                    <TableCell>
                                        {payment.paymentMethod}
                                    </TableCell>

                                    <TableCell>
                                        {payment.paymentStatus}
                                    </TableCell>

                                    <TableCell align="right">
                                        ₹
                                        {formatAmount(
                                            payment.amount
                                        )}
                                    </TableCell>

                                    <TableCell>
                                        {formatDate(
                                            payment.paidDate
                                        )}
                                    </TableCell>

                                </TableRow>

                            ))}

                        </TableBody>

                    </Table>

                </TableContainer>

            )}

        </Box>
    );
}

export default PaymentReport;