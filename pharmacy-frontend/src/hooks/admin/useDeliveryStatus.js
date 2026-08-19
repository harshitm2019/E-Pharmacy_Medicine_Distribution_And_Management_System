import { useQuery } from "@tanstack/react-query";
import { getDeliveryStatus } from "../../services/adminDeliveryService";

function useDeliveryStatus(orderId, enabled = true) {
    return useQuery({
        queryKey: ["delivery-status", orderId],
        queryFn: () => getDeliveryStatus(orderId),
        enabled: !!orderId && enabled
    });
}

export default useDeliveryStatus;