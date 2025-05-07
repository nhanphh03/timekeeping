import {Box, Typography} from '@mui/material';
import {CameraConfigProps} from "../../interfaces/CameraConfigProps.ts";

export default function WebcamFaceDetection(props: Readonly<CameraConfigProps>){
    return (
        <Box sx={{...props.sx}}>
            <div>
                <Typography>{props.cameraConfig?.inputSize}</Typography>
                <Typography>{props.cameraConfig?.modelFaceDetector}</Typography>
                <Typography>{props.cameraConfig?.minConfidence}</Typography>
                <Typography>{props.cameraConfig?.scoreThreshold}</Typography>
            </div>
        </Box>
    );
};
