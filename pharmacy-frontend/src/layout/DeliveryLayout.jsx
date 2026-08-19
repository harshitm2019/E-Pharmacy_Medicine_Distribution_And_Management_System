import MenuIcon from "@mui/icons-material/Menu";

import {
    Box,
    IconButton
} from "@mui/material";

import {
    Outlet
} from "react-router-dom";

import {
    useState
} from "react";

import Header from "../components/common/Header";
import DeliverySidebar from "../components/delivery/DeliverySidebar";


const SIDEBAR_WIDTH = 260;


function DeliveryLayout() {

    const [
        mobileOpen,
        setMobileOpen
    ] = useState(false);


    function handleMobileOpen() {

        setMobileOpen(true);

    }


    function handleMobileClose() {

        setMobileOpen(false);

    }


    return (
        <Box
            sx={{
                display: "flex",
                minHeight: "100vh",
                bgcolor: "#F8FAF9"
            }}
        >

            {/* =========================
                SIDEBAR
               ========================= */}

            <DeliverySidebar
                mobileOpen={
                    mobileOpen
                }
                onMobileClose={
                    handleMobileClose
                }
            />


            {/* =========================
                MAIN CONTENT
               ========================= */}

            <Box
                sx={{
                    flex: 1,
                    minWidth: 0,
                    display: "flex",
                    flexDirection: "column"
                }}
            >

                {/* =========================
                    HEADER
                   ========================= */}

                <Box
                    sx={{
                        position: "relative"
                    }}
                >

                    <Header />

                    {/* Mobile menu button */}

                    <IconButton
                        onClick={
                            handleMobileOpen
                        }
                        sx={{
                            display: {
                                xs: "flex",
                                md: "none"
                            },

                            position:
                                "absolute",

                            left: 12,
                            top: 18,

                            zIndex: 1300,

                            bgcolor:
                                "#FFFFFF",

                            border:
                                "1px solid #E5E7EB",

                            "&:hover": {
                                bgcolor:
                                    "#F5F7FA"
                            }
                        }}
                    >

                        <MenuIcon />

                    </IconButton>

                </Box>


                {/* =========================
                    PAGE CONTENT
                   ========================= */}

                <Box
                    component="main"
                    sx={{
                        flex: 1,
                        p: {
                            xs: 2,
                            sm: 3,
                            md: 4
                        },
                        width: "100%",
                        minWidth: 0
                    }}
                >

                    <Outlet />

                </Box>

            </Box>

        </Box>
    );
}
export default DeliveryLayout;