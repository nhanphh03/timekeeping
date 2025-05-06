import {Box, Grid, SxProps, Theme, Typography} from '@mui/material';
import * as React from "react";

interface FirstItemTimekeepingProps {
    imageSrc: string;
    name: string;
    timeIn: string;
    timeOut: string;
    title: string;
    sx?: SxProps<Theme>;
}

function FirstItemTimekeeping (props: Readonly<FirstItemTimekeepingProps>) {
    return (
        <Grid sx={{backgroundColor: '#e4fcff',
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            height: 110,
            borderRadius: 2,
            px: 2,
            width: '98%',
            ml: 1,
            boxShadow: 3,
            ...props.sx,}}>
            <Grid item size={2} sx={{display: 'flex'}}>
                <Box sx={{m: 'auto',}}>
                    <Typography
                        sx={{
                            borderRadius: '50%',
                            height: 35,
                            width: 35,
                            backgroundColor: '#8a8686',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            color: 'white'
                        }}
                        variant="body2"
                    >
                        01
                    </Typography>
                </Box>
                <Box sx={{display: 'flex', ml: 2}}>
                    <Box>
                        <img
                            src={props.imageSrc}
                            alt={props.name}
                            style={{ width: 74,
                                height: 74,
                                borderRadius: '10%',
                        }}
                        />
                    </Box>
                </Box>
            </Grid>
            <Grid size={5}>
                <Box sx={{ml: 6}}>
                    <Typography sx={{}} variant="subtitle1" fontSize={24} fontWeight={600}>
                        {props.name}
                    </Typography>
                    <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                        {props.title}
                    </Typography>
                </Box>
            </Grid>
            <Grid size={3}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {props.timeIn}
                </Typography>
            </Grid>
            <Grid size={2}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {props.timeOut}
                </Typography>
            </Grid>

        </Grid>
    );
};

export default FirstItemTimekeeping;
