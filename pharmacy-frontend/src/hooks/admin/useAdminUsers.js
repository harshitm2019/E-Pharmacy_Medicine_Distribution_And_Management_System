import { useQuery } from "@tanstack/react-query";
import { getUsers } from "../../services/adminUserService";

function useAdminUsers({ page, size, role, email }) {
    return useQuery({
        queryKey: ["admin-users", page, size, role, email],
        queryFn: () => getUsers({ page, size, role, email })
    });
}

export default useAdminUsers;