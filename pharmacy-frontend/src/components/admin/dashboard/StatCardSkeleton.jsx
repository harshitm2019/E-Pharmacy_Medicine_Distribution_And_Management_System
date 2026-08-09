import { Card, CardContent, Skeleton, Stack } from "@mui/material";

function StatCardSkeleton() {

    return (

        <Card
            elevation={0}
            sx={{
                borderRadius: "18px",
                border: "1px solid #E5E7EB"
            }}
        >

            <CardContent>

                <Stack spacing={2}>

                    <Skeleton
                        variant="text"
                        width="55%"
                        height={28}
                    />

                    <Skeleton
                        variant="text"
                        width="35%"
                        height={55}
                    />

                    <Skeleton
                        variant="rounded"
                        width="100%"
                        height={22}
                    />

                </Stack>

            </CardContent>
        </Card>
    );
}
export default StatCardSkeleton;