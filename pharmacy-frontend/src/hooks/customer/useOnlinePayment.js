import { useMutation, useQueryClient } from "@tanstack/react-query";
import { processOnlinePayment } from "../../services/paymentService";

function useOnlinePayment() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ orderId, paymentMethod }) =>
            processOnlinePayment(orderId, paymentMethod),

        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({
                queryKey: ["my-orders"]
            });

            queryClient.invalidateQueries({
                queryKey: ["my-payments"]
            });

            queryClient.invalidateQueries({
                queryKey: ["order", variables.orderId]
            });
        }
    });
}

export default useOnlinePayment;