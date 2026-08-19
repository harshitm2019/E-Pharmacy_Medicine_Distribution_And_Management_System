import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateMedicineStatus } from "../../services/adminMedicineService";

function useUpdateMedicineStatus() {

    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ medicineIds, status }) => updateMedicineStatus(medicineIds, status),

        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["medicines"] });
        }
    });
}

export default useUpdateMedicineStatus;