import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createMedicine } from "../services/medicineService";

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