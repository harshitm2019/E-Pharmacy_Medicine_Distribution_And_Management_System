import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import { Box, Container, Divider, Stack, Typography } from "@mui/material";

function Footer() {
    return (
       <Box component="footer" sx={{ background: "linear-gradient(135deg, #E8F5E9 0%, #C8E6C9 50%, #A5D6A7 100%)", color: "#1B5E20" }}>
            <Container maxWidth="xl" sx={{ py: 5 }}>
                <Stack direction={{ xs: "column", md: "row" }} justifyContent="space-between" spacing={5}>
                    <Box sx={{ maxWidth: 420 }}>
                        <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 1 }}>
                            <LocalPharmacyIcon sx={{ color: "#2E7D32" }} />
                            <Typography variant="h6" fontWeight={800}>ePharmacy</Typography>
                        </Stack>
                        <Typography sx={{ color: "#455A64", lineHeight: 1.7 }}>
                            ePharmacy is an online pharmacy platform designed to make ordering genuine medicines simple, secure and convenient, with prescription support and doorstep delivery.
                        </Typography>
                    </Box>

                    <Box>
                        <Typography fontWeight={700} sx={{ mb: 1 }}>Contact Us</Typography>
                        <Typography sx={{ color: "#455A64" }}>support@epharmacy.com</Typography>
                        <Typography sx={{ color: "#455A64" }}>+91 98765 43210</Typography>
                        <Typography sx={{ color: "#455A64" }}>India</Typography>
                    </Box>
                </Stack>

                <Divider sx={{ my: 3, borderColor: "rgba(46,125,50,.25)" }} />

                <Typography align="center" sx={{ color: "#455A64", fontSize: 14 }}>
                    © {new Date().getFullYear()} ePharmacy. All rights reserved.
                </Typography>
            </Container>
        </Box>
    );
}

export default Footer;