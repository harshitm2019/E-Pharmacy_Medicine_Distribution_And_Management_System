import { useQuery } from "@tanstack/react-query";
import { getOrderById } from "../../services/adminOrderService";

function useOrderById(orderId) {
    return useQuery({
        queryKey: ["order", orderId],
        queryFn: () => getOrderById(orderId),
        enabled: !!orderId
    });
}

export default useOrderById;