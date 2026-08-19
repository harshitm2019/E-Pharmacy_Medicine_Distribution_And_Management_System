import { useMutation, useQueryClient } from "@tanstack/react-query";
import { changeEmail } from "../../services/userService";

function useChangeEmail() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: changeEmail,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["my-profile"] });
        }
    });
}

export default useChangeEmail;