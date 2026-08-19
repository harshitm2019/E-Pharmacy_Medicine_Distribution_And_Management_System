import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateMedicine } from "../../services/adminMedicineService";

function useUpdateMedicine() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ medicineId, data }) => updateMedicine(medicineId, data),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["medicines"] })
    });
}

export default useUpdateMedicine;