import { useQuery } from "@tanstack/react-query";
import { getDeliveryStatus } from "../../services/deliveryService";

function useDeliveryStatus(orderId, enabled = true) {
    return useQuery({
        queryKey: ["delivery-status", orderId],
        queryFn: () => getDeliveryStatus(orderId),
        enabled: !!orderId && enabled,
        retry: false
    });
}

export default useDeliveryStatus;