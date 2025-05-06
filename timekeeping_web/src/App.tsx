import {Box, Grid} from '@mui/material';
import Stack from '@mui/system/Stack';
import TimekeepingList from "./components/item/TimekeepingList.js";
import DayItemList from "./components/item/DayItemList.jsx";
import WebcamFaceDetectionV2 from "./components/WebcamFaceDetectionV2.jsx";
import Footer from "./components/layout/Footer.js";
import FirstItemTimekeeping from "./components/item/FirstItemTimekeeping";
import { CameraConfig } from './interfaces/CameraConfig.js';
import React from "react";

function App() {

    const [cameraConfig, setCameraConfig] = React.useState<CameraConfig | null>(null);

    const handleSaveConfig = (config: CameraConfig) => {
        console.log('Saved config:', config);
        setCameraConfig(config); // Cập nhật state với config mới
    };

    return (
        <Grid container sx={{ height: '100vh' }}>
            <Grid item size={5} sx={{ height: '100%' }}>
                <Box sx={{ height: '54%',
                    width: '100%',
                    borderRadius: 2,
                    boxShadow: 3,}}>
                    <WebcamFaceDetectionV2 videoWidth={750} videoHeight={500} sx={{pt: 2, pl: 2 }} />
                </Box>
                <Box sx={{ height: '1%', width: '100%' }}></    Box>
                <Box sx={{
                    borderRadius: 2,
                    boxShadow: 3,
                    height: '45%',
                    width: '100%' }}>
                </Box>
            </Grid>

            <Grid item size={7} sx={{ height: '100%' }} >
                <Box sx={{ height: '1%', width: '100%'}}></Box>
                <Box sx={{ height: '10%', width: '100%'}}>
                    <DayItemList ></DayItemList>
                </Box>
                <Box sx={{ height: '12%', width: '100%'}}>
                    <FirstItemTimekeeping
                        imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                        name="Phạm Hữu Nhân"
                        title="Lotte Finance"
                        timeIn="08:11:22"
                        timeOut="17:21:33">
                    </FirstItemTimekeeping>
                </Box>
                <Box sx={{ height: '69%', width: '100%', overflowY: 'auto'}}>
                    <Stack spacing={1} sx={{}}>
                        <TimekeepingList></TimekeepingList>
                    </Stack>
                </Box>
                <Box sx={{ height: '1%', width: '100%'}}></Box>
                <Box sx={{ height: '7%', width: '98%', ml: 2}}>
                    <Footer/>
                </Box>
            </Grid>
        </Grid>
    );
}

export default App;
