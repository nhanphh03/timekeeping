import {Grid} from '@mui/material';
import * as React from 'react';
import CameraAndTimeComponent from "./components/layout/CameraAndTimeComponent";
import DayAndTimekeepingComponent from "./components/layout/DayAndTimekeepingComponent";

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

