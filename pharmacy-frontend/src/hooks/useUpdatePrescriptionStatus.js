import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updatePrescriptionStatus } from "../services/adminOrderService";

function useUpdatePrescriptionStatus() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ orderId, prescriptionStatus }) => updatePrescriptionStatus(orderId, prescriptionStatus),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["orders"] })
    });
}

export default useUpdatePrescriptionStatus;