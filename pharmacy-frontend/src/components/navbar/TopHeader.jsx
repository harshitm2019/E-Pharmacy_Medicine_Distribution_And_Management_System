import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import { Badge, Box, Button, Container, IconButton, Toolbar, Typography } from "@mui/material";
import { NavLink } from "react-router-dom";

function TopHeader() {

    return (

        <Container maxWidth="xl">

            <Toolbar disableGutters sx={{ height: 85 }}>

                {/* Logo */}
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 1,
                        cursor: "pointer",
                        flexShrink: 0
                    }}
                >
                    <LocalPharmacyIcon
                        sx={{
                            fontSize: 42,
                            color: "primary.main"
                        }}
                    />

                    <Box>

                        <Typography
                            variant="h5"
                            fontWeight={800}
                            color="primary.main"
                            lineHeight={1}
                        >
                            ePharmacy
                        </Typography>

                        <Typography
                            variant="caption"
                            color="text.secondary"
                        >
                            Your Health Partner
                        </Typography>

                    </Box>

                </Box>

                {/* Search */}

                <Box
                    sx={{
                        flex: 1,
                        mx: 6,
                        boxShadow: "0 10px 30px rgba(0,0,0,.06)"
                    }}
                >
                   
                </Box>
                {/* Actions */}

                <Box sx={{ display: "flex", alignItems: "center", gap: 1.5 }}>
 
                    <Button
                        component={NavLink}
                        to="/login"
                        variant="outlined"
                        sx={{
                            px: 3,
                            py: 1,
                            borderRadius: "12px",
                            textTransform: "none",
                            fontWeight: 700,
                            borderWidth: 2,
                            transition: ".3s",
                            "&:hover": {
                                borderWidth: 2,
                                transform: "translateY(-2px)"
                            }
                        }}
                    >

                        Login

                    </Button>

                    <Button
                        component={NavLink}
                        to="/register"
                        variant="contained"
                        sx={{
                            px: 3,
                            py: 1,
                            borderRadius: "12px",
                            textTransform: "none",
                            fontWeight: 700,
                            background: "linear-gradient(135deg,#4CAF50,#43A047)",
                            boxShadow: "0 10px 25px rgba(46,125,50,.25)",
                            transition: ".3s",
                            "&:hover": {
                                background: "linear-gradient(135deg,#43A047,#2E7D32)",
                                boxShadow: "0 12px 30px rgba(46,125,50,.35)",
                                transform: "translateY(-2px)"
                            }
                        }}
                    >

                        Register
                    </Button>
                </Box>
            </Toolbar>
        </Container>
    );
}
export default TopHeader;