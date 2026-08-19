import { Box, Button, Stack, Typography } from "@mui/material";
import { useState } from "react";
import OrderReport from "./OrderReport";
import PaymentReport from "./PaymentReport";

function Reports() {

    const [report, setReport] = useState("order");

    return (
        <Box>

            <Typography
                variant="h4"
                fontWeight={700}
                sx={{ mb: 3 }}
            >
                Reports
            </Typography>

            <Stack
                direction={{
                    xs: "column",
                    sm: "row"
                }}
                spacing={2}
                sx={{ mb: 4 }}
            >

                <Button
                    variant={
                        report === "order"
                            ? "contained"
                            : "outlined"
                    }
                    onClick={() =>
                        setReport("order")
                    }
                >
                    Order Report
                </Button>

                <Button
                    variant={
                        report === "payment"
                            ? "contained"
                            : "outlined"
                    }
                    onClick={() =>
                        setReport("payment")
                    }
                >
                    Payment Report
                </Button>

            </Stack>

            {report === "order" && (
                <OrderReport />
            )}

            {report === "payment" && (
                <PaymentReport />
            )}

        </Box>
    );
}

export default Reports;