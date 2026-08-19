import { useQuery } from "@tanstack/react-query";
import { getMyPrescriptions } from "../../services/prescriptionService";

function usePrescriptions({ page, size }) {
    return useQuery({
        queryKey: ["prescriptions", page, size],
        queryFn: () => getMyPrescriptions({ page, size })
    });
}

export default usePrescriptions;