import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import MedicalServicesIcon from "@mui/icons-material/MedicalServices";
import VerifiedUserIcon from "@mui/icons-material/VerifiedUser";

import { Box, Button, Stack, Typography } from "@mui/material";

function HeroContent() {

    return (

        <Stack spacing={5}>

            {/* Badge */}

            <Box
                sx={{
                    display: "inline-flex",
                    alignItems: "center",
                    gap: 1,
                    px: 2,
                    py: 1,
                    bgcolor: "#E8F5E9",
                    borderRadius: "30px",
                    width: "fit-content"
                }}
            >

                <VerifiedUserIcon color="success" fontSize="small" />

                <Typography
                    fontWeight={600}
                    color="success.main"
                >
                    India's Trusted Online Pharmacy
                </Typography>

            </Box>

            {/* Heading */}

            <Typography
                sx={{
                    fontSize: {
                        xs: "48px",
                        md: "68px"
                    },
                    fontWeight: 800,
                    lineHeight: 1.05,
                    letterSpacing: "-2px"
                }}
            >

                Your Health

                <br />

                <Box
                    component="span"
                    sx={{
                        color: "primary.main"
                    }}
                >

                    Delivered Faster.

                </Box>

            </Typography>

            {/* Description */}

            <Typography
                sx={{
                    fontSize: "20px",
                    lineHeight: 1.9,
                    color: "#546E7A",
                    fontWeight: 400,
                    maxWidth: 620,
                    letterSpacing: ".2px"
                }}
            >

                Order genuine medicines, healthcare products and wellness essentials with secure prescriptions, affordable prices and doorstep delivery.

            </Typography>

            {/* Buttons */}

            <Stack
                direction="row"
                spacing={2}
            >

                <Button
                    variant="contained"
                    size="large"
                    endIcon={<ArrowForwardIcon />}
                    sx={{
                        px: 4,
                        py: 1.5,
                        borderRadius: "40px",
                        textTransform: "none",
                        fontWeight: 700,
                        boxShadow: "0 10px 25px rgba(46,125,50,.25)",

                        "&:hover": {
                            boxShadow: "0 15px 35px rgba(46,125,50,.35)"
                        }
                    }}
                >

                    Shop Medicines

                </Button>

                <Button
                    variant="outlined"
                    size="large"
                    sx={{
                        px: 4,
                        py: 1.5,
                        borderRadius: "40px",
                        textTransform: "none",
                        fontWeight: 700
                    }}
                >

                    Upload Prescription

                </Button>

            </Stack>

            {/* Statistics */}

            <Box mt={2}>

                <Stack
                    direction="row"
                    spacing={8}
                >

                    <Box>

                        <Typography
                            variant="h4"
                            fontWeight={800}
                            color="primary.main"
                        >
                            5000+
                        </Typography>

                        <Typography color="text.secondary">
                            Medicines
                        </Typography>

                    </Box>

                    <Box>

                        <Typography
                            variant="h4"
                            fontWeight={800}
                            color="primary.main"
                        >
                            25K+
                        </Typography>

                        <Typography color="text.secondary">
                            Happy Customers
                        </Typography>

                    </Box>

                    <Box>

                        <Typography
                            variant="h4"
                            fontWeight={800}
                            color="primary.main"
                        >
                            24×7
                        </Typography>

                        <Typography color="text.secondary">
                            Support
                        </Typography>

                    </Box>

                </Stack>

            </Box>

            {/* Trust Features */}

            <Stack spacing={2.5}>

                <Stack direction="row" spacing={2} alignItems="center">

                    <MedicalServicesIcon color="success" />

                    <Typography fontSize={17}>
                        Genuine medicines from licensed pharmacies
                    </Typography>

                </Stack>

                <Stack direction="row" spacing={2} alignItems="center">

                    <LocalShippingIcon color="primary" />

                    <Typography fontSize={17}>
                        Fast and secure doorstep delivery
                    </Typography>

                </Stack>

                <Stack direction="row" spacing={2} alignItems="center">

                    <VerifiedUserIcon color="success" />

                    <Typography fontSize={17}>
                        100% secure online ordering experience
                    </Typography>

                </Stack>

            </Stack>

        </Stack>

    );

}

export default HeroContent;