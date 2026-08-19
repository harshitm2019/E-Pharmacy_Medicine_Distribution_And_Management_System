import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateUserStatus } from "../../services/adminUserService";

function useUpdateUserStatus() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ userId, status }) => updateUserStatus(userId, status),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["admin-users"] });
        }
    });
}

export default useUpdateUserStatus;