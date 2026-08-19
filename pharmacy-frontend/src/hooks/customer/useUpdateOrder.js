import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateOrder } from "../../services/orderService";

function useUpdateOrder() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ orderId, request }) =>
            updateOrder(orderId, request),

        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({
                queryKey: ["my-orders"]
            });

            queryClient.invalidateQueries({
                queryKey: ["order", variables.orderId]
            });

            queryClient.invalidateQueries({
                queryKey: ["my-payments"]
            });
        }
    });
}

export default useUpdateOrder;