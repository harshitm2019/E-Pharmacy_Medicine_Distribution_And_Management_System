import { useQuery } from "@tanstack/react-query";
import { getAllPayments } from "../../services/adminPaymentService";

function useAllPayments() {
    return useQuery({
        queryKey: ["admin-payments"],
        queryFn: getAllPayments,
        enabled : false
    });
}

export default useAllPayments;