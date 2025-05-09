import {Box, Typography} from "@mui/material";
import WebcamFaceDetectionV2 from "../camera/WebcamFaceDetectionV2";
import * as React from "react";
import RealTimeComponent from "../items/clock/RealTimeComponent";
import DescriptionComponent from "../items/clock/DescriptionComponent";
import LogoComponent from "../items/logo/LogoComponent"
import LocationOnIcon from '@mui/icons-material/LocationOn';

export default function CameraAndTimeComponent() {
    return (
        <Box sx={{height: '100%'}}>
            <Box sx={{
                height: '54%',
                width: '100%',
                borderRadius: 2,
                boxShadow: 3,
            }}>
                {/*<WebcamFaceDetection cameraConfig={cameraConfig} videoWidth={750}*/}
                {/*                     videoHeight={500} sx={{pt: 2, pl: 2}}/>*/}
                <WebcamFaceDetectionV2 sx={{pt: 2, pl: 2}}/>
            </Box>
            <Box sx={{height: '1%', width: '100%'}}/>

            <Box sx={{
                borderRadius: 2,
                boxShadow: 3,
                height: '37%',
                width: '100%'
            }}>
                <Box sx={{height: '15%'}}>
                    <DescriptionComponent/>
                </Box>
                <Box sx={{height: '40%'}}>
                    <RealTimeComponent/>
                </Box>
                <Box sx={{height: '45%'}}>
                    <LogoComponent/>
                </Box>
            </Box>

            <Box sx={{
                borderRadius: 2,
                boxShadow: 3,
                height: '8%',
                width: '100%'
            }}>
                <Box sx={{height: '15%', display: 'flex', justifyContent: 'space-between', alignItems: 'center', pt: 5}}>
                    <Box sx={{display: 'flex', alignItems: 'center'}}>
                        <LocationOnIcon sx={{height: 37, width: 37, ml: 2}} />
                        <Typography fontSize={19} fontWeight={500}>
                            Timekeeping Website
                        </Typography>
                    </Box>

                    <Typography sx={{mr: 2}} fontStyle="italic" fontSize={17} fontWeight={500}>
                        © 2025 - Copyright belongs to Human Corporation
                    </Typography>
                </Box>

            </Box>

        </Box>
    );
};