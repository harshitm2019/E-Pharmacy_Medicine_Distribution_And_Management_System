import {
    useMutation,
    useQueryClient
} from "@tanstack/react-query";

import {
    updateDeliveryStatus
} from "../../services/deliveryService";


function useUpdateDeliveryStatus() {

    const queryClient =
        useQueryClient();


    return useMutation({
        mutationFn: ({
            orderId,
            status,
            cashCollected
        }) =>
            updateDeliveryStatus(
                orderId,
                status,
                cashCollected
            ),

        onSuccess: () => {

            queryClient.invalidateQueries({
                queryKey: [
                    "my-delivery-orders"
                ]
            });

            queryClient.invalidateQueries({
                queryKey: [
                    "delivery-boy-dashboard"
                ]
            });
        }
    });
}
export default useUpdateDeliveryStatus;