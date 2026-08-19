import { useQuery } from "@tanstack/react-query";
import { getOrderReport } from "../../services/adminOrderService";

function useOrderReport({ startDate, endDate, page, size }) {
    return useQuery({
        queryKey: ["order-report", startDate, endDate, page, size],
        queryFn: () => getOrderReport({ startDate, endDate, page, size }),
        enabled: !!startDate && !!endDate
    });
}

export default useOrderReport;