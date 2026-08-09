import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateCategory } from "../services/categoryService";

function useUpdateCategory() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ categoryId, data }) => updateCategory(categoryId, data),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["categories"] })
    });
}

export default useUpdateCategory;