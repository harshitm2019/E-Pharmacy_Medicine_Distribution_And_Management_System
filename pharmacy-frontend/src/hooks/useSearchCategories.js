import { useQuery } from "@tanstack/react-query";
import { searchCategories } from "../services/categoryService";

function useSearchCategories(keyword) {
    return useQuery({
        queryKey: ["categories", "search", keyword],
        queryFn: () => searchCategories(keyword),
        enabled: keyword.trim().length > 0
    });
}

export default useSearchCategories;