import {Box, Typography} from "@mui/material"

export default function LogoComponent() {
    return (
        <Box sx={{mt: 3,
                display: "flex",
                justifyContent: "center",}}>

            <Box sx={{mr: 2}}>
                <img
                    src="../../../../public/icon.png"
                    alt="logo.png"
                    style={{
                        height: 80,
                        width: 80,
                        border: '2px solid #8a8686',
                        borderRadius: '50%'
                    }}
                />
            </Box>

            <Typography sx={{ fontSize: "5vh",
                fontWeight: 800,
                color: "#3e3e3e" }}>
                Human Timekeeper
            </Typography>
        </Box>

    );
};