import { Box, Container, Grid } from "@mui/material";

import HeroContent from "./HeroContent";
import HeroImage from "./HeroImage";

function HeroSection() {

    return (

        <Box
            sx={{
                position: "relative",

                overflow: "hidden",

                pt: 10,

                pb: 10,

                minHeight: "calc(100vh - 150px)",

                background: `
                    radial-gradient(circle at top left,#E8F5E9 0%,transparent 40%),
                    radial-gradient(circle at bottom right,#E3F2FD 0%,transparent 40%),
                    linear-gradient(135deg,#FAFFFB 0%,#F7FBFF 100%)
                `
            }}
        >

            {/* Top Left Glow */}

            <Box
                sx={{
                    position: "absolute",

                    width: 320,

                    height: 320,

                    borderRadius: "50%",

                    bgcolor: "#4CAF50",

                    opacity: .08,

                    filter: "blur(90px)",

                    top: -120,

                    left: -120
                }}
            />

            {/* Bottom Right Glow */}

            <Box
                sx={{
                    position: "absolute",

                    width: 320,

                    height: 320,

                    borderRadius: "50%",

                    bgcolor: "#2196F3",

                    opacity: .08,

                    filter: "blur(90px)",

                    bottom: -120,

                    right: -120
                }}
            />
            <Container maxWidth="xl">

                <Grid
                    container
                    spacing={10}
                    alignItems="center"
                >

                    <Grid size={{ xs: 12, lg: 6 }}>

                        <HeroContent />

                    </Grid>

                    <Grid size={{ xs: 12, lg: 6 }}>

                        <HeroImage />

                    </Grid>

                </Grid>

            </Container>

        </Box>

    );

}

export default HeroSection;