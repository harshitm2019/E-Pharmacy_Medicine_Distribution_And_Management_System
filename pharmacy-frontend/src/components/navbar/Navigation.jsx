import { Box, Button, Container } from "@mui/material";
import { NavLink } from "react-router-dom";

const menus = [
    { name: "Home", path: "/" },
    { name: "Medicines", path: "/medicines" },
    { name: "Categories", path: "/categories" },
    { name: "Upload Prescription", path: "/prescriptions" },
    { name: "Contact Us", path: "/contact" }
];

function Navigation() {

    return (
        <Box sx={{ borderTop: "1px solid #eceff1", borderBottom: "1px solid #eceff1", bgcolor: "#fff" }}>
            <Container maxWidth="xl">
                <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", gap: 4, height: 55 }}>

                    {
                        menus.map(menu => (

                            <Button
                                key={menu.name}
                                component={NavLink}
                                to={menu.path}
                                sx={{
                                    color: "#37474F",
                                    textTransform: "none",
                                    fontWeight: 600,
                                    fontSize: "15px",
                                    borderRadius: 0,
                                    position: "relative",
                                    px: 1,

                                    "&::after": {
                                        content: '""',
                                        position: "absolute",
                                        left: 0,
                                        bottom: -8,
                                        width: "0%",
                                        height: "3px",
                                        bgcolor: "primary.main",
                                        transition: ".3s"
                                    },

                                    "&:hover": {
                                        color: "primary.main",
                                        bgcolor: "transparent"
                                    },

                                    "&:hover::after": {
                                        width: "100%"
                                    },

                                    "&.active": {
                                        color: "primary.main"
                                    },

                                    "&.active::after": {
                                        width: "100%"
                                    }
                                }}
                            >

                                {menu.name}

                            </Button>

                        ))
                    }
                </Box>
            </Container>
        </Box>
    );
}

export default Navigation;