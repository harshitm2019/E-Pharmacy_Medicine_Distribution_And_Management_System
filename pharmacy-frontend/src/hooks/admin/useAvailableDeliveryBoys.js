import { useQuery } from "@tanstack/react-query";
import { getAvailableDeliveryBoys } from "../../services/adminDeliveryService";

function useAvailableDeliveryBoys({ page, size }) {
    return useQuery({
        queryKey: ["available-delivery-boys", page, size],
        queryFn: () => getAvailableDeliveryBoys({ page, size })
    });
}

export default useAvailableDeliveryBoys;