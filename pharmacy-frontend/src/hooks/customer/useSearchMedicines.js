import { useQuery } from "@tanstack/react-query";
import { searchMedicines } from "../../services/medicineService";

function useSearchMedicines({ keyword, page, size }) {
    return useQuery({
        queryKey: ["customer-medicines", "search", keyword, page, size],
        queryFn: () => searchMedicines({ keyword, page, size }),
        enabled: !!keyword?.trim(),
        staleTime: 30000
    });
}

export default useSearchMedicines;