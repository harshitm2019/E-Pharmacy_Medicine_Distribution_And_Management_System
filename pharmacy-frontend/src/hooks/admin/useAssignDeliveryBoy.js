import { useMutation, useQueryClient } from "@tanstack/react-query";
import { assignDeliveryBoy } from "../../services/adminDeliveryService";

function useAssignDeliveryBoy() {

    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: assignDeliveryBoy,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["orders"] });
            queryClient.invalidateQueries({ queryKey: ["delivery-boys"] });
            queryClient.invalidateQueries({ queryKey: ["available-delivery-boys"] });
        }
    });
}

export default useAssignDeliveryBoy;