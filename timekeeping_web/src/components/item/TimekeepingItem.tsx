import {Box, Grid, SxProps, Theme, Typography} from '@mui/material';
import * as React from "react";
import {ReactNode} from "react";

interface TimekeepingItemProps {
    index: ReactNode;
    imageSrc: string;
    name: string;
    timeIn: string;
    timeOut: string;
    title: string;
    sx?: SxProps<Theme>;
}
function TimekeepingItem(props: Readonly<TimekeepingItemProps>) {
    return (
        <Grid sx={{backgroundColor: props.index % 2 === 0 ? '#ffffff' : '#ececec',
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            height: 110,
            borderRadius: 2,
            boxShadow: 3,
            px: 2,
            ...props.sx,}}>
            <Grid item xs={2}>
                <Box sx={{display: 'flex'}}>
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
                            {props.index}
                        </Typography>
                    </Box>
                    <Box>
                        <img
                            border='1px solid #8a8686'
                            src={props.imageSrc}
                            alt={props.name}
                            style={{ width: 74, height: 74, borderRadius: '10%'}}
                        />
                    </Box>
                </Box>
            </Grid>
            <Grid item xs={5}>
                <Box>
                    <Typography sx={{}} variant="subtitle1" fontSize={24} fontWeight={600}>
                        {props.name}
                    </Typography>
                    <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                        {props.title}
                    </Typography>
                </Box>
            </Grid>
            <Grid item xs={3}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {props.timeIn}
                </Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {props.timeOut}
                </Typography>
            </Grid>

        </Grid>
    );
};

export default TimekeepingItem;