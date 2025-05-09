import {Box, Grid} from "@mui/material";
import DayItemList from "../items/day/DayItemList";
import FirstItemTimekeeping from "../items/timekeeping/FirstItemTimekeeping";
import Stack from "@mui/system/Stack";
import TimekeepingList from "../items/timekeeping/TimekeepingList";
import Footer from "./Footer";
import * as React from "react";
import {CameraConfig} from "../../interfaces/CameraConfig";

export default function DayAndTimekeepingComponent() {
    const [cameraConfig, setCameraConfig] = React.useState<CameraConfig | null>(null);

    return (
        <Grid sx={{height: '100%'}}>
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
    );
};