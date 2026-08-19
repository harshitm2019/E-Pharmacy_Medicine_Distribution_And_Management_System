import { useQuery } from "@tanstack/react-query";
import { getMedicinesByCategory } from "../../services/medicineService";

function useMedicinesByCategory({ categoryId, page, size }) {
    return useQuery({
        queryKey: ["customer-medicines", "category", categoryId, page, size],
        queryFn: () => getMedicinesByCategory({ categoryId, page, size }),
        enabled: !!categoryId,
        staleTime: 30000
    });
}

export default useMedicinesByCategory;