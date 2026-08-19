import { useQuery } from "@tanstack/react-query";
import { getMyDeliveryOrders } from "../../services/deliveryService";

function useMyDeliveryOrders(status, page = 0) {

    return useQuery({
        queryKey: [ "my-delivery-orders", status,page],
        queryFn: () =>getMyDeliveryOrders(status,page),
        enabled: !!status
    });
}
export default useMyDeliveryOrders;