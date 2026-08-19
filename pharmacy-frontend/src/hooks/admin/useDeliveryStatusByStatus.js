import { useQuery } from "@tanstack/react-query";
import { getDeliveryStatusByStatus } from "../../services/adminDeliveryService";

function useDeliveryStatusByStatus({ status, page, size }) {
    return useQuery({
        queryKey: ["delivery-status", status, page, size],
        queryFn: () => getDeliveryStatusByStatus({ status, page, size }),
        enabled: !!status
    });
}

export default useDeliveryStatusByStatus;