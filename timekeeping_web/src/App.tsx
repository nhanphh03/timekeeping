import {Box, Grid} from '@mui/material';
import Stack from '@mui/system/Stack';
import {CameraConfig} from './interfaces/CameraConfig.js';
import WebcamFaceDetection from "./components/camera/WebcamFaceDetection";
import DayItemList from "./components/items/DayItemList";
import FirstItemTimekeeping from "./components/items/FirstItemTimekeeping";
import TimekeepingList from "./components/items/TimekeepingList";
import Footer from "./components/layout/Footer";
// @ts-ignore
import React from "react";

export default function App() {

    const [cameraConfig, setCameraConfig] = React.useState<CameraConfig | null>(null);

    return (
        <Grid container sx={{height: '100vh'}}>
            <Grid item xs={5} sx={{height: '100%'}}>
                <Box sx={{
                    height: '54%',
                    width: '100%',
                    borderRadius: 2,
                    boxShadow: 3,
                }}>
                    <WebcamFaceDetection cameraConfig={cameraConfig} videoWidth={750}
                                         videoHeight={500} sx={{pt: 2, pl: 2}}/>
                </Box>
                <Box sx={{height: '1%', width: '100%'}}/>
                <Box sx={{
                    borderRadius: 2,
                    boxShadow: 3,
                    height: '45%',
                    width: '100%'
                }}>
                </Box>
            </Grid>

            <Grid item xs={7} sx={{height: '100%'}}>
                <Box sx={{height: '1%', width: '100%'}}></Box>
                <Box sx={{height: '10%', width: '100%'}}>
                    <DayItemList/>
                </Box>
                <Box sx={{height: '12%', width: '100%'}}>
                    <FirstItemTimekeeping
                        imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                        name="Phạm Hữu Nhân"
                        title="Lotte Finance"
                        timeIn="08:11:22"
                        timeOut="17:21:33"/>
                </Box>
                <Box sx={{height: '69%', width: '100%', overflowY: 'auto'}}>
                    <Stack spacing={1} sx={{}}>
                        <TimekeepingList/>
                    </Stack>
                </Box>
                <Box sx={{height: '1%', width: '100%'}}></Box>
                <Box sx={{height: '7%', width: '98%', ml: 2}}>
                    <Footer onSave={setCameraConfig}/>
                </Box>
            </Grid>
        </Grid>
    );
}

