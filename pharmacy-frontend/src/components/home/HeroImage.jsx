import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import MedicalServicesIcon from "@mui/icons-material/MedicalServices";
import ShieldIcon from "@mui/icons-material/Shield";

import { Box, Paper } from "@mui/material";

import FloatingCard from "./FloatingCard";

function HeroImage() {

    return (

        <Box
            sx={{
                position: "relative",
                height: 560,
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}
        >

            {/* Background Circle */}

            <Box
                sx={{
                    position: "absolute",
                    width: 400,
                    height: 400,
                    borderRadius: "50%",
                    background: "linear-gradient(135deg,#A5D6A7,#81D4FA)",
                    opacity: .9
                }}
            />

            {/* Main Card */}

            <Paper
                elevation={0}
                sx={{
                    width: 250,
                    height: 280,
                    borderRadius: "30px",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    position: "relative",
                    zIndex: 2,
                    background: "rgba(255,255,255,.75)",
                    backdropFilter: "blur(14px)",
                    border: "1px solid rgba(255,255,255,.5)",
                    boxShadow: "0 20px 60px rgba(0,0,0,.12)",
                    transition: ".3s",

                    "&:hover": {
                        transform: "translateY(-8px)"
                    }
                }}
            >

                <MedicalServicesIcon
                    sx={{
                        fontSize: 150,
                        color: "primary.main"
                    }}
                />

            </Paper>

            {/* Floating Cards */}

            <FloatingCard
                position="top-left"
                icon={<ShieldIcon />}
                title="100% Genuine"
                subtitle="Certified Medicines"
            />

            <FloatingCard
                position="bottom-left"
                icon={<LocalHospitalIcon />}
                title="5000+ Medicines"
                subtitle="Trusted Brands"
            />

            <FloatingCard
                position="top-right"
                icon={<LocalShippingIcon />}
                title="Fast Delivery"
                subtitle="Within 1 Hour"
            />

        </Box>

    );

}

export default HeroImage;