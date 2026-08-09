import { useQuery } from "@tanstack/react-query";
import { getOrders } from "../services/adminOrderService";

function useOrders({ page, size, status }) {
    return useQuery({
        queryKey: ["orders", page, size, status],
        queryFn: () => getOrders({ page, size, status })
    });
}

export default useOrders;