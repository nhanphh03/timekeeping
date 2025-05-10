import {Box, Typography} from "@mui/material";
import * as React from "react";

export default function DescriptionComponent() {
    return (
        <Box
            sx={{
                width: "100%",
                height: "8vh",
                borderRadius: 2,
                textAlign: "center",
                lineHeight: "4vh",
            }}
        >
            <Typography
                sx={{
                    fontSize: "3.5vh",
                    fontWeight: 800,
                    color: "#3e3e3e",
                }}
            >
                Please look at the camera.
            </Typography>
        </Box>
    );
};