import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cancelOrder } from "../services/adminOrderService";

function useCancelOrder() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: cancelOrder,
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["orders"] })
    });
}

export default useCancelOrder;