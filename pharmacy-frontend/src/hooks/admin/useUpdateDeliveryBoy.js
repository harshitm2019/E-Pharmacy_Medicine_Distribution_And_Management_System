import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateDeliveryBoy } from "../../services/adminDeliveryService";

function useUpdateDeliveryBoy() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ deliveryBoyId, data }) => updateDeliveryBoy(deliveryBoyId, data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["delivery-boys"] });
        }
    });
}

export default useUpdateDeliveryBoy;