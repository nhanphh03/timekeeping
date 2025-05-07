import {Box, Grid, InputAdornment, Typography} from '@mui/material';
import TextField from '@mui/material/TextField';
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import ChangeConfigCamera from '../dialog/ChangeConfigCamera';
import {CameraConfigProps} from "../../interfaces/CameraConfigProps";

export default function Footer(props: Readonly<CameraConfigProps>) {
    return (
        <Grid container sx={{
            backgroundColor: '#ffffff',
            display: 'flex',
            alignItems: 'center',
            borderRadius: 2,
            px: 2,
            width: '100%',
            height: '100%',
            boxShadow: 3
        }}>

            <Grid item xs={1}>
                <Box sx={{ml: 1}}>
                    <ChangeConfigCamera onSave={props.onSave}/>
                </Box>
            </Grid>
            <Grid item xs={5}>
                <Box
                    component="form"
                    sx={{'& > :not(style)': {width: '100%'}}}
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
                                    <IconButton type="button" sx={{p: '5px'}} aria-label="search">
                                        <SearchIcon/>
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </Box>
            </Grid>
            <Grid item xs={3}>
                <Box sx={{display: 'flex', float: 'right'}}>
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
                <Box sx={{ml: 2, float: 'right'}}>
                    <Stack direction="row" spacing={2}>
                        <IconButton>
                            <ArrowBackIosIcon/>
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
                        <IconButton>
                            <ArrowForwardIosIcon/>
                        </IconButton>
                    </Stack>

                </Box>
            </Grid>

        </Grid>
    );
};
