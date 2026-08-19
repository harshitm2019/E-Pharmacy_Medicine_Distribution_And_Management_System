import { useQuery } from "@tanstack/react-query";

import {
    getDeliveryBoyDashboard
} from "../../services/dashboardService";


function useDeliveryBoyDashboard() {

    return useQuery({
        queryKey: ["delivery-boy-dashboard"],
        queryFn: getDeliveryBoyDashboard
    });

}


export default useDeliveryBoyDashboard;