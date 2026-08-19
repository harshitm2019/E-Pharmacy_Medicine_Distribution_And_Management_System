import { Box, Card, CardContent, Typography } from "@mui/material";

function StatCard({
    title,
    value,
    icon,
    color
}) {

    return (

        <Card
            elevation={0}
            sx={{
                borderRadius: "18px",
                border: "1px solid #E5E7EB",
                transition: ".3s",
                "&:hover": {
                    transform: "translateY(-4px)",
                    boxShadow: "0 12px 30px rgba(0,0,0,.08)"
                }
            }}
        >

            <CardContent
                sx={{
                    p: 3,
                    "&:last-child": {
                        pb: 3
                    }
                }}
            >

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "flex-start",
                        gap: 2
                    }}
                >

                    <Box
                        sx={{
                            flex: 1,
                            minWidth: 0
                        }}
                    >

                        <Typography
                            sx={{
                                fontSize: "16px",
                                fontWeight: 600,
                                color: "#64748B",
                                mb: 2
                            }}
                        >
                            {title}
                        </Typography>

                        <Typography
                            sx={{
                                fontSize: "42px",
                                fontWeight: 800,
                                color: "#1E293B",
                                lineHeight: 1
                            }}
                        >
                            {value}
                        </Typography>

                    </Box>

                    <Box
                        sx={{
                            width: 64,
                            height: 64,
                            borderRadius: "18px",
                            bgcolor: color,
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            color: "#FFFFFF",
                            flexShrink: 0,

                            "& svg": {
                                fontSize: 34
                            }
                        }}
                    >
                        {icon}
                    </Box>

                </Box>

            </CardContent>

        </Card>

    );

}

export default StatCard;