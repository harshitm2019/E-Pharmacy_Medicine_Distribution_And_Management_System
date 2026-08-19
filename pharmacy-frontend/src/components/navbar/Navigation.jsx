import { Box, Button, Container } from "@mui/material";
import toast from "react-hot-toast";
import { useLocation, useNavigate } from "react-router-dom";

const menus = [
    { name: "Home", target: "home", protected: false },
    { name: "Medicines", path: "customer/medicines", protected: true },
    { name: "Categories", path: "customer/medicines", protected: true },
    { name: "About Us", target: "about", protected: false }
];

function Navigation() {

    const navigate = useNavigate();
    const location = useLocation();

function handleNavigation(event, menu) {

    if (menu.target) {

        event.preventDefault();

        if (location.pathname !== "/") {

            navigate("/");

            setTimeout(() => {
                document
                    .getElementById(menu.target)
                    ?.scrollIntoView({
                        behavior: "smooth"
                    });
            }, 50);

        } else {

            document
                .getElementById(menu.target)
                ?.scrollIntoView({
                    behavior: "smooth"
                });
        }

        return;
    }


    if (menu.protected) {

        const token =
            localStorage.getItem("token");

        if (!token) {

            event.preventDefault();

            toast.error(
                "Login as a customer."
            );

            navigate("/login");

            return;
        }


        const user = JSON.parse(
            localStorage.getItem("user")
        );

        if (user?.role !== "CUSTOMER") {

            event.preventDefault();

            toast.error(
                "Login as a customer."
            );

            navigate("/login");

            return;
        }


        /*
         * CUSTOMER
         */

        event.preventDefault();

        navigate(menu.path);

        return;
    }
}

    return (
        <Box
            sx={{
                borderTop:
                    "1px solid #eceff1",
                borderBottom:
                    "1px solid #eceff1",
                bgcolor: "#fff",
                position: "sticky",
                top: 0,
                zIndex: 1100
            }}
        >

            <Container maxWidth="xl">

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        gap: 4,
                        height: 55
                    }}
                >

                    {menus.map(menu => (

                        <Button
                            key={menu.name}
                            onClick={event =>
                                handleNavigation(
                                    event,
                                    menu
                                )
                            }
                            sx={{
                                color: "#37474F",
                                textTransform:
                                    "none",
                                fontWeight: 600,
                                fontSize: "15px",
                                borderRadius: 0,
                                position: "relative",
                                px: 1,

                                "&::after": {
                                    content: '""',
                                    position:
                                        "absolute",
                                    left: 0,
                                    bottom: -8,
                                    width: "0%",
                                    height: "3px",
                                    bgcolor:
                                        "primary.main",
                                    transition: ".3s"
                                },

                                "&:hover": {
                                    color:
                                        "primary.main",
                                    bgcolor:
                                        "transparent"
                                },

                                "&:hover::after": {
                                    width: "100%"
                                }
                            }}
                        >
                            {menu.name}
                        </Button>

                    ))}

                </Box>

            </Container>

        </Box>
    );
}

export default Navigation;