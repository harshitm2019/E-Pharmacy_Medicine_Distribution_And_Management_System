import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cancelOrder } from "../../services/orderService";

function useCancelOrder() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: cancelOrder,

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["my-orders"]
            });

            queryClient.invalidateQueries({
                queryKey: ["order"]
            });
        }
    });
}

export default useCancelOrder;