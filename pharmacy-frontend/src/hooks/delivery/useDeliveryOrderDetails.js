import { useQuery } from "@tanstack/react-query";

import { getDeliveryOrderDetails } from "../../services/deliveryService";


function useDeliveryOrderDetails(orderId) {

    return useQuery({
        queryKey: [
            "delivery-order-details",
            orderId
        ],
        queryFn: () =>     getDeliveryOrderDetails(orderId),
        enabled: !!orderId
    });
}
export default useDeliveryOrderDetails;