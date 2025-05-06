import {Box, Stack, Typography} from "@mui/material";
import { DayItemListProps } from "../../interfaces/DayItemListProps";


export default function TestProp({ cameraConfig }: DayItemListProps) {

    return (
        <Box>
            <Stack direction="row" spacing={1.5} sx={{pt: 1, pl: 1}}>
                <Typography variant="h6">Camera Configuration</Typography>
                {cameraConfig ? (
                    <Box>
                        <Typography>Model Face Detector: {cameraConfig.modelFaceDetector}</Typography>
                        {cameraConfig.modelFaceDetector === 'ssd_mobilenetv1' && (
                            <Typography>Min Confidence: {cameraConfig.minConfidence}</Typography>
                        )}
                        {cameraConfig.modelFaceDetector === 'tiny_face_detector' && (
                            <>
                                <Typography>Input Size: {cameraConfig.inputSize}</Typography>
                                <Typography>Score Threshold: {cameraConfig.scoreThreshold}</Typography>
                            </>
                        )}
                    </Box>
                ) : (
                    <Typography>No configuration saved yet.</Typography>
                )}
                {/*<DayItem child = 'Monday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Tuesday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Wednesday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Thursday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Friday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Saturday' status = 'active'   sx={{width: '10%', height: 60}}></DayItem>*/}
                {/*<DayItem child = 'Sunday' status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>*/}
            </Stack>
        </Box>

    )
}
