import { useMutation, useQueryClient } from "@tanstack/react-query";
import { checkoutOrder } from "../../services/orderService";

function useCheckout() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: checkoutOrder,

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["my-orders"]
            });

            queryClient.invalidateQueries({
                queryKey: ["my-payments"]
            });
        }
    });
}
export default useCheckout;