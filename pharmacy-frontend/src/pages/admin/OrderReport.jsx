import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

import OrderReportTable from "../../components/admin/report/OrderReportTable";
import useOrderReport from "../../hooks/admin/useOrderReport";

function OrderReport() {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [searchStartDate, setSearchStartDate] = useState("");
    const [searchEndDate, setSearchEndDate] = useState("");
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    const { data, isLoading, isError, error } = useOrderReport({
        startDate: searchStartDate,
        endDate: searchEndDate,
        page,
        size
    });

    useEffect(() => {
        if (isError) {
            toast.error(error?.response?.data?.message || "Failed to generate order report.");
        }
    }, [isError, error]);

    function handleGenerate() {
        if (!startDate || !endDate) {
            toast.error("Please select both start date and end date.");
            return;
        }

        if (startDate > endDate) {
            toast.error("Start date cannot be after end date.");
            return;
        }

        setPage(0);
        setSearchStartDate(startDate);
        setSearchEndDate(endDate);
    }

    return (
        <Box>
            <Typography variant="h4" fontWeight={700} sx={{ mb: 3 }}>
                Order Report
            </Typography>

            <Stack direction={{ xs: "column", sm: "row" }} spacing={2} sx={{ mb: 3 }}>
                <Box>
                    <Typography fontSize={14} fontWeight={600} sx={{ mb: 0.5 }}>
                        Start Date
                    </Typography>
                    <TextField
                        type="date"
                        value={startDate}
                        onChange={e => setStartDate(e.target.value)}
                        size="small"
                    />
                </Box>

                <Box>
                    <Typography fontSize={14} fontWeight={600} sx={{ mb: 0.5 }}>
                        End Date
                    </Typography>
                    <TextField
                        type="date"
                        value={endDate}
                        onChange={e => setEndDate(e.target.value)}
                        size="small"
                    />
                </Box>

                <Button
                    variant="contained"
                    onClick={handleGenerate}
                    disabled={!startDate || !endDate}
                    sx={{ alignSelf: "flex-end" }}
                >
                    Generate Report
                </Button>
            </Stack>

            <OrderReportTable
                data={data}
                isLoading={isLoading}
                page={page}
                size={size}
                setPage={setPage}
                setSize={setSize}
            />
        </Box>
    );
}
export default OrderReport;