import {
    Box,
    Chip,
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


function getReturnStatusColor(status) {

    switch (status) {

        case "PENDING":
            return "warning";

        case "APPROVED":
            return "info";

        case "REJECTED":
            return "error";

        case "REFUNDED":
            return "success";

        default:
            return "default";
    }
}


function MyReturns({
    data,
    isLoading
}) {

    if (isLoading) {

        return (
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    py: 8
                }}
            >
                <CircularProgress />
            </Box>
        );
    }


    const returns =
        data?.data?.content ??
        data?.data ??
        [];


    if (returns.length === 0) {

        return (
            <Box
                sx={{
                    textAlign: "center",
                    py: 8
                }}
            >

                <Typography
                    variant="h6"
                    fontWeight={700}
                >
                    No Returns Found
                </Typography>


                <Typography
                    color="text.secondary"
                    sx={{ mt: 1 }}
                >
                    Your return requests will appear
                    here.
                </Typography>

            </Box>
        );
    }


    return (
        <TableContainer
            component={Paper}
            elevation={0}
            sx={{
                border:
                    "1px solid #E5E7EB",
                borderRadius: 2
            }}
        >

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell>
                            <strong>
                                Return ID
                            </strong>
                        </TableCell>

                        <TableCell>
                            <strong>
                                Order ID
                            </strong>
                        </TableCell>

                        <TableCell>
                            <strong>
                                Reason
                            </strong>
                        </TableCell>

                        <TableCell>
                            <strong>
                                Status
                            </strong>
                        </TableCell>

                        <TableCell>
                            <strong>
                              Requested Return Date 
                            </strong>
                        </TableCell>

                        <TableCell>
                            <strong>
                                Processed Date
                            </strong>
                        </TableCell>

                    </TableRow>

                </TableHead>


                <TableBody>

                    {returns.map(item => (

                        <TableRow
                            key={
                                item.returnId
                            }
                        >

                            <TableCell>
                                #{item.returnId}
                            </TableCell>


                            <TableCell>
                                #{item.orderId}
                            </TableCell>


                            <TableCell
                                sx={{
                                    maxWidth: 300
                                }}
                            >
                                {item.returnReason}
                            </TableCell>


                            <TableCell>

                                <Chip
                                    label={
                                        item.returnStatus
                                    }
                                    size="small"
                                    color={
                                        getReturnStatusColor(
                                            item.returnStatus
                                        )
                                    }
                                />

                            </TableCell>


                            <TableCell>
                                {item.returnDate
                                    ? new Date(
                                        item.returnDate
                                    ).toLocaleDateString()
                                    : "—"}
                            </TableCell>


                            <TableCell>
                                {item.processedDate
                                    ? new Date(
                                        item.processedDate
                                    ).toLocaleString()
                                    : "—"}
                            </TableCell>

                        </TableRow>

                    ))}

                </TableBody>

            </Table>

        </TableContainer>
    );
}
export default MyReturns;