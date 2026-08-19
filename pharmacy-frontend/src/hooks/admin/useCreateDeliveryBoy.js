import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createDeliveryBoy } from "../../services/adminDeliveryService";

function useCreateDeliveryBoy() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: createDeliveryBoy,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["delivery-boys"] });
            queryClient.invalidateQueries({ queryKey: ["available-delivery-boys"] });
        }
    });
}

export default useCreateDeliveryBoy;