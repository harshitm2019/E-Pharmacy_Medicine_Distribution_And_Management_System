import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography } from "@mui/material";

function UserManagementTable({ data, isLoading, page, setPage, size, setSize, onStatusChange }) {
    const users = data?.data?.content ?? [];
    const pageData = data?.data?.page;

    if (isLoading) {
        return <Typography>Loading users...</Typography>;
    }

    return (
        <Paper elevation={0} sx={{ border: "1px solid #E5E7EB", borderRadius: 3, overflow: "hidden" }}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Role</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell align="center">Action</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {users.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={5} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary">No users found</Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            users.map(user => (
                                <TableRow key={user.userId} hover>
                                    <TableCell>{user.userId}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.role}</TableCell>
                                    <TableCell>{user.status}</TableCell>
                                    <TableCell align="center">
                                        <Button
                                            size="small"
                                            variant="outlined"
                                            color={user.status === "ACTIVE" ? "error" : "success"}
                                            onClick={() =>
                                                onStatusChange(
                                                    user.userId,
                                                    user.status === "ACTIVE" ? "INACTIVE" : "ACTIVE"
                                                )
                                            }
                                        >
                                            {user.status === "ACTIVE" ? "Deactivate" : "Activate"}
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
export default UserManagementTable;