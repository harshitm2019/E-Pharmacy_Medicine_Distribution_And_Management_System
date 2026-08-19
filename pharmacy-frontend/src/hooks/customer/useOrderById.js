import { useQuery } from "@tanstack/react-query";
import { getOrderById } from "../../services/orderService";

function useOrderById(orderId, enabled = true) {
    return useQuery({
        queryKey: ["order", orderId],
        queryFn: () => getOrderById(orderId),
        enabled: !!orderId && enabled
    });
}

export default useOrderById;