import {useEffect, useState} from "react";
import {Box, Typography} from "@mui/material";

const clockBoxStyle = {
    height: 120,
    width: 120,
    backgroundColor: "#3e3e3e",
    color: "#ffffff",
    boxShadow: 3,
    borderRadius: 2,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
}; 


interface Time {
    hh: string;
    mm: string;
    ss: string;
}

export default function RealTimeComponent() {
    const [time, setTime] = useState<Time>(getCurrentTime());

    useEffect(() => {
        const interval = setInterval(() => {
            setTime(getCurrentTime());
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    return (
        <Box >
            <Box
                sx={{
                    width: "100%",
                    mt: 3,
                    display: "flex",
                    justifyContent: "center", // center horizontally
                    alignItems: "center", // center vertically
                    gap: 2,
                }}
            >
                <Box sx={clockBoxStyle}>
                    <Typography sx={{
                        fontSize: "8vh",
                        fontWeight: 800}}>
                        {time.hh}
                    </Typography>
                </Box>

                <Typography sx={{ fontSize: "8vh",
                    fontWeight: 800,
                    color: "#3e3e3e" }}>
                    :
                </Typography>

                <Box sx={clockBoxStyle}>
                    <Typography sx={{
                        fontSize: "8vh",
                        fontWeight: 800}}>
                        {time.mm}
                    </Typography>
                </Box>

                <Typography sx={{ fontSize: "8vh",
                    fontWeight: 800,
                    color: "#3e3e3e" }}>
                    :
                </Typography>

                <Box sx={clockBoxStyle}>
                    <Typography sx={{
                        fontSize: "8vh",
                        fontWeight: 800}}>
                        {time.ss}
                    </Typography>
                </Box>
            </Box>
        </Box>
    );
}

function getCurrentTime(): Time {
    const now = new Date();
    return {
        hh: String(now.getHours()).padStart(2, "0"),
        mm: String(now.getMinutes()).padStart(2, "0"),
        ss: String(now.getSeconds()).padStart(2, "0"),
    };
}
