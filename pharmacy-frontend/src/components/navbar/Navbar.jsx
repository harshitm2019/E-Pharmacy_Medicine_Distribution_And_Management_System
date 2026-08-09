import { AppBar } from "@mui/material";
import Navigation from "./Navigation";
import TopHeader from "./TopHeader";
import { Link } from "react-router-dom";

function Navbar() {

    return (

        <AppBar  position="sticky"color="inherit"elevation={2}
        >
            <TopHeader />

            <Navigation />

        </AppBar>

    );

}

export default Navbar;