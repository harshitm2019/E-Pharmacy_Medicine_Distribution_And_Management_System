import { useMutation, useQueryClient } from "@tanstack/react-query";
import { replacePrescription } from "../../services/prescriptionService";

function useReplacePrescription() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ prescriptionId, file }) =>
            replacePrescription(prescriptionId, file),

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["prescriptions"]
            });
        }
    });
}
export default useReplacePrescription;