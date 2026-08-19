import { useQuery } from "@tanstack/react-query";
import { getDeliveryBoys } from "../../services/adminDeliveryService";

function useDeliveryBoys({ page, size }) {
    return useQuery({
        queryKey: ["delivery-boys", page, size],
        queryFn: () => getDeliveryBoys({ page, size })
    });
}

export default useDeliveryBoys;