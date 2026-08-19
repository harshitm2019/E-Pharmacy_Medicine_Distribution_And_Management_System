import { useQuery } from "@tanstack/react-query";

import { getAdminDashboard } from "../../services/dashboardService";

function useDashboard() {

    return useQuery({

        queryKey: ["admin-dashboard"],

        queryFn: getAdminDashboard,

        staleTime: 30000,

        refetchOnWindowFocus: true

    });

}

export default useDashboard;