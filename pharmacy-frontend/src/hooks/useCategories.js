import { useQuery } from "@tanstack/react-query";

import { getCategories } from "../services/categoryService";

function useCategories() {

    return useQuery({

        queryKey: ["categories"],

        queryFn: getCategories,

        staleTime: 30000

    });
}
export default useCategories;