import { Avatar, Box, Typography } from "@mui/material";

const positions = {

    "top-left": {
        top: 25,
        left: 0
    },

    "bottom-left": {
        bottom: 100,
        left:-65
    },

    "top-right": {
        bottom: 500,
        right: 0
    }

};

function FloatingCard({ icon, title, subtitle, position }) {

    return (

        <Box
            sx={{
                position: "absolute",
                ...positions[position],

                width: 210,
                display: "flex",
                alignItems: "center",
                gap: 2,
                p: 2,
                borderRadius: "22px",
                bgcolor: "rgba(255,255,255,.88)",
                backdropFilter: "blur(18px)",
                border: "1px solid rgba(255,255,255,.6)",
                boxShadow: "0 20px 50px rgba(0,0,0,.10)",
                zIndex: 5,
                transition: ".35s",
                animation: "floating 6s ease-in-out infinite",
                "&:hover": {
                    transform: "translateY(-8px)"
                },
                "@keyframes floating": {

                    "0%": {

                        transform: "translateY(0px)"

                    },

                    "50%": {

                        transform: "translateY(-12px)"

                    },

                    "100%": {

                        transform: "translateY(0px)"

                    }

                }

            }}
        >
            <Avatar
                sx={{
                    bgcolor: "#E8F5E9",
                    color: "primary.main",
                    width: 54,
                    height: 54
                }}
            >

                {icon}

            </Avatar>

            <Box>

                <Typography
                    fontWeight={700}
                    fontSize={18}
                >
                    {title}
                </Typography>

                <Typography
                    color="text.secondary"
                    fontSize={14}
                >
                    {subtitle}
                </Typography>

            </Box>

        </Box>
    );
}
export default FloatingCard;