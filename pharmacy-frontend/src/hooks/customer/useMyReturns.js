import { useQuery } from "@tanstack/react-query";

import { getMyReturns } from "../../services/returnService";

function useMyReturns(page = 0, size = 10) {
    return useQuery({ 
        queryKey: ["my-returns", page,size],
        queryFn: () => getMyReturns(page, size)
    });
}
export default useMyReturns;