import { Box } from "@mui/material";
import Footer from "../components/home/Footer";
import HeroSection from "../components/home/HeroSection";
import Navbar from "../components/navbar/Navbar";

function Home() {
    return (
        <Box sx={{ minHeight: "100vh" }}>
            <Navbar />
            <Box id="home">
                <HeroSection />
            </Box>
            <Box id="about">
                <Footer />
            </Box>
        </Box>
    );
}

export default Home;