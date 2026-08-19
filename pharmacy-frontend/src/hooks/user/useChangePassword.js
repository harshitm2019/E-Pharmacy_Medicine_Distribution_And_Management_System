import { useMutation } from "@tanstack/react-query";
import { changePassword } from "../../services/userService";

function useChangePassword() {
    return useMutation({
        mutationFn: changePassword
    });
}

export default useChangePassword;