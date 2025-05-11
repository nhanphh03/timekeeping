import {Box, Grid} from "@mui/material";
import DayItemList from "../items/day/day-item-list";
import FirstItemTimekeeping from "../items/timekeeping/first-item-timekeeping";
import Stack from "@mui/system/Stack";
import TimekeepingList from "../items/timekeeping/timekeeping-list";
import Footer from "./footer";
import * as React from "react";
import {CameraConfig, TimekeepingItemProps} from "../types";
import {useEffect, useState} from "react";
import {firstDetection} from "../service/APIService";

export default function DayAndTimekeepingComponent() {
    const [cameraConfig, setCameraConfig] = React.useState<CameraConfig | null>(null);

    const [data, setData] = useState<TimekeepingItemProps>();
    useEffect(() => {
        firstDetection('http://localhost:8080/api/detection/first')
            .then(setData)
            .catch((err) => console.error('Error fetching timekeeping data:', err));
    }, []);

    return (
        <Grid sx={{height: '100%'}}>
            <Box sx={{height: '1%', width: '100%'}}></Box>
            <Box sx={{height: '10%', width: '100%'}}>
                <DayItemList/>
            </Box>
            <Box sx={{height: '12%', width: '100%'}}>
                <FirstItemTimekeeping {...data}/>
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