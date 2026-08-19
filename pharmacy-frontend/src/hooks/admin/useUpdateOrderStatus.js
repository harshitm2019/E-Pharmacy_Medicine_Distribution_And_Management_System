import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateOrderStatus } from "../../services/adminOrderService";

function useUpdateOrderStatus() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ orderId, orderStatus }) => updateOrderStatus(orderId, orderStatus),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["orders"] })
    });
}

export default useUpdateOrderStatus;