import { useMutation } from "@tanstack/react-query";

import { createReturn } from "../../services/returnService";

function useCreateReturn() {

    return useMutation({
        mutationFn: createReturn
    });
}
export default useCreateReturn;