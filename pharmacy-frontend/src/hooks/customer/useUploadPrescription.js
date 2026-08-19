import { useMutation, useQueryClient } from "@tanstack/react-query";
import { uploadPrescription } from "../../services/prescriptionService";

function useUploadPrescription() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ file, doctorName }) =>
            uploadPrescription(file, doctorName),

        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["prescriptions"]
            });
        }
    });
}

export default useUploadPrescription;