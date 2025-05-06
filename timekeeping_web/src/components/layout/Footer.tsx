import {Box, Grid, InputAdornment, SxProps, Theme, Typography} from '@mui/material';
import TextField from '@mui/material/TextField';
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import SettingsIcon from '@mui/icons-material/Settings';
import ChangeConfigCamera from '../dialog/ChangeConfigCamera';
import { CameraConfig } from '../../interfaces/CameraConfig';
import * as React from "react";
import TestProp from '../item/TestProp';

interface FooterProps {
    sx?: SxProps<Theme>;
    open: boolean;
    onClose: (value: string) => void;
}


function Footer({sx}: FooterProps) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = (value: string) => {
        setOpen(false);
    };

    const [cameraConfig, setCameraConfig] = React.useState<CameraConfig | null>(null);

    const handleSaveConfig = (config: CameraConfig) => {
        setCameraConfig(config); 
    };

    return (
        <Grid sx={{backgroundColor: '#ffffff',
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            borderRadius: 2,
            px: 2,
            width: '100%',
            height: '100%',
            boxShadow: 3,
            ...sx,}}>

            <Grid item xs={1}>
                <Box sx={{ml: 1, mt: 1}}>
                    <ChangeConfigCamera  onSave={handleSaveConfig}></ChangeConfigCamera>
                    <TestProp cameraConfig={cameraConfig} />
                </Box>
            </Grid>
            <Grid item xs={5} sx={{height: '100%'}}>
                <Box
                    component="form"
                    sx={{ '& > :not(style)': { mt: 2, width: '100%'} }}
                    noValidate
                    autoComplete="off"
                >
                    <TextField
                        size="small"
                        id="outlined-basic"
                        label="Search"
                        variant="outlined"
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton type="button" sx={{ p: '5px' }} aria-label="search">
                                        <SearchIcon />
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </Box>
            </Grid>
            <Grid item xs={3}>
                <Box sx={{display: 'flex', ml: 3}}>
                    <Typography
                        sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            color: 'black'
                        }}
                    >
                        Rows per page:
                    </Typography>
                    <Select sx={{ml: 2}}
                            size='small'
                        labelId="demo-simple-select-label"
                        id="demo-simple-select">
                        <MenuItem value={10}>10</MenuItem>
                        <MenuItem value={20}>20</MenuItem>
                        <MenuItem value={30}>30</MenuItem>
                    </Select>
                </Box>
            </Grid>
            <Grid item xs={3}>
                <Box sx={{ml: 2}}>
                    <Stack direction="row" spacing={2}>
                        <IconButton>
                            <ArrowBackIosIcon />
                        </IconButton>
                        <Typography
                            sx={{
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                                color: 'black'
                            }}
                        >
                            1-10 of 200
                        </Typography>
                        <IconButton >
                            <ArrowForwardIosIcon />
                        </IconButton>
                    </Stack>

                </Box>
            </Grid>

        </Grid>
    );
};

export default Footer;
