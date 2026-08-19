import { useQuery } from "@tanstack/react-query";
import { getMyPayments } from "../../services/paymentService";

function useMyPayments() {
    return useQuery({
        queryKey: ["my-payments"],
        queryFn: getMyPayments
    });
}

export default useMyPayments;