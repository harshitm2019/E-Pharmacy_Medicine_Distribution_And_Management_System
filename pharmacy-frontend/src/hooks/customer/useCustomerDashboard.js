import { useQuery } from "@tanstack/react-query";
import { getCustomerDashboard } from "../../services/dashboardService";

function useCustomerDashboard() {
    return useQuery({
        queryKey: ["customer-dashboard"],
        queryFn: getCustomerDashboard
    });
}

export default useCustomerDashboard;