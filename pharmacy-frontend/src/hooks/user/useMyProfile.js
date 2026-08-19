import { useQuery } from "@tanstack/react-query";
import { getMyProfile } from "../../services/userService";

function useMyProfile() {
    return useQuery({
        queryKey: ["my-profile"],
        queryFn: getMyProfile
    });
}

export default useMyProfile;