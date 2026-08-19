import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createMedicine } from "../../services/adminMedicineService";

function useCreateMedicine() {

    const queryClient = useQueryClient();

    return useMutation({

        mutationFn: createMedicine,

        onSuccess: () => {

            queryClient.invalidateQueries({

                queryKey: ["medicines"]

            });
        }
    });
}
export default useCreateMedicine;