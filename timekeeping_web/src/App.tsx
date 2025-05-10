import {Grid} from '@mui/material';
import * as React from 'react';
import CameraAndTimeComponent from "./components/layout/camera-and-time-component";
import DayAndTimekeepingComponent from "./components/layout/day-and-timekeeping-component";

export default function App() {
    return (
        <Grid container sx={{height: '100vh'}}>
            <Grid item xs={5}>
                <CameraAndTimeComponent/>
            </Grid>
            <Grid item xs={7}>
                <DayAndTimekeepingComponent/>
            </Grid>
        </Grid>
    );
}

