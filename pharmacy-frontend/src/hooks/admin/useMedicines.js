import { useQuery } from "@tanstack/react-query";

import { getMedicines } from "../../services/adminMedicineService";

function useMedicines({

    page,
    size,
    keyword,
    categoryId,
    status

}) {

    return useQuery({

        queryKey: [

            "medicines",
            page,
            size,
            keyword,
            categoryId,
            status

        ],

        queryFn: () =>

            getMedicines({

                page,
                size,
                keyword,
                categoryId,
                status

            }),

        keepPreviousData: true,
        staleTime: 30000

    });

}

export default useMedicines;