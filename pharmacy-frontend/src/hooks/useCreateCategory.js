import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createCategory } from "../services/categoryService";

function useCreateCategory() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: createCategory,
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["categories"] })
    });
}

export default useCreateCategory;