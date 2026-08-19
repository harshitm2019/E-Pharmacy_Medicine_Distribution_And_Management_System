import { useQuery } from "@tanstack/react-query";
import { getReturnsByStatus } from "../../services/returnService";

function useReturns({ status, page, size }) {
    return useQuery({
        queryKey: ["returns", status, page, size],
        queryFn: () => getReturnsByStatus({ status, page, size }),
        enabled: !!status
    });
}

export default useReturns;