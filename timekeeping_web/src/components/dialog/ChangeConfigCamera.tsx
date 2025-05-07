import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import SettingsIcon from '@mui/icons-material/Settings';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import Slider from '@mui/material/Slider';
import {CameraConfigProps} from "../../interfaces/CameraConfigProps.ts";

export default function ChangeConfigCamera({onSave}: CameraConfigProps) {
    const [open, setOpen] = React.useState(false);
    const [modelFaceDetector, setModelFaceDetector] = React.useState('');
    const [minConfidence, setMinConfidence] = React.useState(0.5);
    const [inputSize, setInputSize] = React.useState(512);
    const [scoreThreshold, setScoreThreshold] = React.useState(0.5);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleChange = (event: SelectChangeEvent) => {
        setModelFaceDetector(event.target.value as string);
    };

    const handleSave = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const config = {
            modelFaceDetector,
            ...(modelFaceDetector === 'ssd_mobilenetv1' && {minConfidence}),
            ...(modelFaceDetector === 'tiny_face_detector' && {inputSize, scoreThreshold}),
        };
        if (onSave) {
            onSave(config);
        }
        handleClose();
    };
    return (
        <React.Fragment>
            <SettingsIcon fontSize="medium"
                          onClick={handleClickOpen}
                          sx={{cursor: 'pointer'}}/>
            <Dialog
                open={open}
                onClose={handleClose}
                slotProps={{
                    paper: {
                        component: 'form',
                        onSubmit: handleSave,
                        sx: {
                            width: 350,
                            maxWidth: '90vw',
                            minWidth: 300,
                            height: 340,
                        },
                    },
                }}
            >
                <DialogTitle>Change Camera Config</DialogTitle>
                <DialogContent>
                    <Box sx={{display: 'flex', gap: 2, pt: 2}}>
                        <FormControl fullWidth size="small">
                            <InputLabel id="model-face-detector-label">Model Face Detector</InputLabel>
                            <Select
                                labelId="model-face-detector-label"
                                id="model-face-detector"
                                value={modelFaceDetector}
                                label="Model Face Detector"
                                onChange={handleChange}
                            >
                                <MenuItem value={'ssd_mobilenetv1'}>SSD Mobilenet V1</MenuItem>
                                <MenuItem value={"tiny_face_detector"}>Tiny Face Detector</MenuItem>
                            </Select>
                        </FormControl>
                    </Box>
                    <Box sx={{display: 'flex', gap: 2, mt: 4}}>
                        {modelFaceDetector === 'ssd_mobilenetv1' && (
                            <Box sx={{width: 300}}>
                                <label>Min Confidence:</label>
                                <Slider
                                    value={minConfidence}
                                    onChange={(event, value) => {
                                        console.debug(event);
                                        setMinConfidence(value as number);
                                    }}
                                    defaultValue={0.5}
                                    valueLabelDisplay="auto"
                                    step={0.1}
                                    marks
                                    min={0.0}
                                    max={1.0}
                                />
                            </Box>
                        )}
                        {modelFaceDetector === 'tiny_face_detector' && (
                            <Box>
                                <FormControl fullWidth size="small">
                                    <InputLabel id="tiny_face_detector_input_size_label">Input Size</InputLabel>
                                    <Select
                                        labelId="tiny_face_detector_input_size_label"
                                        id="tiny_face_detector_input_size"
                                        value={inputSize}
                                        onChange={(e) => setInputSize(Number(e.target.value))}
                                        label="Input Size"
                                    >
                                        {[128, 160, 224, 320, 416, 512, 608].map(size => (
                                            <MenuItem key={size} value={size}>{`${size} x ${size}`}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>

                                <Box sx={{width: 300, mt: 3}}>
                                    <label>Score Threshold:</label>
                                    <Slider
                                        value={scoreThreshold}
                                        onChange={(event, value) => {
                                            console.debug(event);
                                            setScoreThreshold(value as number);
                                        }}
                                        valueLabelDisplay="auto"
                                        step={0.1}
                                        marks
                                        min={0.1}
                                        max={1.0}
                                    />
                                </Box>

                            </Box>
                        )}
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} sx={{color: 'black'}}>Cancel</Button>
                    <Button type="submit">Save</Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
        ;
}
