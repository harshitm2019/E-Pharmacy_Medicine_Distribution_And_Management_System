import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateReturnStatus } from "../../services/returnService";

function useUpdateReturnStatus() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ returnId, returnStatus }) => updateReturnStatus(returnId, returnStatus),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["returns"] });
        }
    });
}

export default useUpdateReturnStatus;