import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateProfile } from "../../services/userService";

function useUpdateProfile() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: updateProfile,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["my-profile"] });
        }
    });
}

export default useUpdateProfile;