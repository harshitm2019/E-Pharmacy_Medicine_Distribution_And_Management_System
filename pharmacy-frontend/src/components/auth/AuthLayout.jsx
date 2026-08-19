import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import SecurityIcon from "@mui/icons-material/Security";
import VerifiedUserIcon from "@mui/icons-material/VerifiedUser";
import { Box, Button, Container, Grid, Paper, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";

function AuthLayout({ title, subtitle, children }) {
    const navigate = useNavigate();

    return (
        <Box
            sx={{
                minHeight: "100vh",
                display: "flex",
                alignItems: "center",
                background: "linear-gradient(135deg,#F8FFF9 0%,#EEF8FF 100%)",
                py: { xs: 3, sm: 5 }
            }}
        >
            <Container maxWidth="md" sx={{ maxWidth: "1000px !important" }}>
                <Paper
                    elevation={0}
                    sx={{
                        overflow: "hidden",
                        borderRadius: { xs: "20px", md: "28px" },
                        boxShadow: "0 25px 70px rgba(0,0,0,.10)"
                    }}
                >
                    <Grid container>

                        {/* Left Section */}

                        <Grid
                            size={{ xs: 12, md: 6 }}
                            sx={{
                                display: { xs: "none", md: "block" }
                            }}
                        >
                            <Box
                                sx={{
                                    height: "100%",
                                    p: { md: 5, lg: 6 },
                                    display: "flex",
                                    flexDirection: "column",
                                    justifyContent: "center",
                                    background: "linear-gradient(135deg,#E8F5E9 0%,#A5D6A7 55%,#4CAF50 100%)"
                                }}
                            >
                                <Stack direction="row" spacing={2} alignItems="center" mb={5}>
                                    <LocalPharmacyIcon sx={{ color: "#1B5E20", fontSize: 48 }} />

                                    <Typography
                                        sx={{
                                            fontSize: "34px",
                                            fontWeight: 800,
                                            color: "#1B5E20"
                                        }}
                                    >
                                        ePharmacy
                                    </Typography>
                                </Stack>

                                <Typography
                                    sx={{
                                        fontSize: { md: "42px", lg: "48px" },
                                        fontWeight: 800,
                                        lineHeight: 1.1,
                                        letterSpacing: "-1px",
                                        color: "#1B5E20",
                                        mb: 3
                                    }}
                                >
                                    Your Health
                                    <br />

                                    <Box component="span" sx={{ color: "#2E7D32" }}>
                                        Our Priority.
                                    </Box>
                                </Typography>

                                <Typography
                                    sx={{
                                        fontSize: "16px",
                                        lineHeight: 1.8,
                                        color: "#455A64",
                                        mb: 5
                                    }}
                                >
                                    Order genuine medicines, upload prescriptions securely and get fast doorstep delivery from India's trusted online pharmacy.
                                </Typography>

                                <Stack spacing={3}>
                                    <Stack direction="row" spacing={2} alignItems="center">
                                        <VerifiedUserIcon sx={{ color: "#2E7D32" }} />
                                        <Typography sx={{ fontWeight: 600, color: "#1B5E20" }}>
                                            Genuine Medicines
                                        </Typography>
                                    </Stack>

                                    <Stack direction="row" spacing={2} alignItems="center">
                                        <LocalShippingIcon sx={{ color: "#2E7D32" }} />
                                        <Typography sx={{ fontWeight: 600, color: "#1B5E20" }}>
                                            Fast Doorstep Delivery
                                        </Typography>
                                    </Stack>

                                    <Stack direction="row" spacing={2} alignItems="center">
                                        <SecurityIcon sx={{ color: "#2E7D32" }} />
                                        <Typography sx={{ fontWeight: 600, color: "#1B5E20" }}>
                                            Secure Payment
                                        </Typography>
                                    </Stack>
                                </Stack>
                            </Box>
                        </Grid>

                        {/* Right Section */}

                        <Grid size={{ xs: 12, md: 6 }}>
                            <Box
                                sx={{
                                    minHeight: { xs: "auto", md: 620 },
                                    height: "100%",
                                    p: { xs: 3, sm: 4, md: 5, lg: 6 },
                                    display: "flex",
                                    flexDirection: "column",
                                    justifyContent: "center",
                                    bgcolor: "#FFFFFF"
                                }}
                            >

                                {/* Home Button */}

                                <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 3 }}>
                                    <Button
                                        variant="outlined"
                                        startIcon={<HomeOutlinedIcon />}
                                        onClick={() => navigate("/")}
                                        sx={{
                                            borderRadius: "10px",
                                            textTransform: "none",
                                            fontWeight: 600
                                        }}
                                    >
                                        Home
                                    </Button>
                                </Box>

                                <Typography
                                    sx={{
                                        fontSize: { xs: "30px", sm: "34px", md: "38px" },
                                        fontWeight: 800,
                                        lineHeight: 1.15,
                                        letterSpacing: "-1px",
                                        color: "#1B5E20",
                                        mb: 1
                                    }}
                                >
                                    {title}
                                </Typography>

                                <Typography
                                    sx={{
                                        fontSize: "16px",
                                        color: "#607D8B",
                                        lineHeight: 1.7,
                                        maxWidth: 420,
                                        mb: 4
                                    }}
                                >
                                    {subtitle}
                                </Typography>
                                {children}
                            </Box>
                        </Grid>
                   </Grid>
                </Paper>
            </Container>
        </Box>
    );
}
export default AuthLayout;