import { useQuery } from "@tanstack/react-query";
import { getMyOrders } from "../../services/orderService";

function useMyOrders() {
    return useQuery({
        queryKey: ["my-orders"],
        queryFn: getMyOrders
    });
}

export default useMyOrders;