
    import {
    Avatar,
    Box,
    Typography
} from "@mui/material";

    import useAuth from "../../hooks/useAuth";

    function Header() {

        const { user } = useAuth();

        return (

            <Box
                sx={{
                    height: 80,
                    bgcolor: "#FFFFFF",
                    borderBottom: "1px solid #E5E7EB",
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    px: 4,
                    position: "sticky",
                    top: 0,
                    zIndex: 1200,
                
                }}
            >

                {/* Left */}

                <Box>

                    <Typography
                        sx={{
                            fontSize: "28px",
                            fontWeight: 800,
                            color: "#263238"
                        }}
                    >
                        Dashboard
                    </Typography>

                    <Typography
                        sx={{
                            color: "#78909C",
                            fontSize: "14px"
                        }}
                    >
                        Welcome back, {user?.username}
                    </Typography>

                </Box>

                {/* Right */}

                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 3
                    }}
                >
                    <Box
                        sx={{
                            display: "flex",
                            alignItems: "center",
                            gap: 2,
                            cursor: "pointer"
                        }}
                    >

                        <Avatar
                            sx={{
                                bgcolor: "#2E7D32",
                                width: 46,
                                height: 46,
                                fontWeight: 700
                            }}
                        >
                            {user?.username?.charAt(0)}
                        </Avatar>

                        <Box>

                            <Typography
                                sx={{
                                    fontWeight: 700,
                                    color: "#263238"
                                }}
                            >
                                {user?.username}
                            </Typography>

                            <Typography
                                sx={{
                                    color: "#78909C",
                                    fontSize: "13px"
                                }}
                            >
                                {user?.role}
                            </Typography>
                        </Box>
                    </Box>
                </Box>
            </Box>
        );
    }
    export default Header;