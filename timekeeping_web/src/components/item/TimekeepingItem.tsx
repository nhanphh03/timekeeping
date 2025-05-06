import {Box, Grid, Typography} from '@mui/material';
import * as React from "react";
import TimekeepingItemProps from "../../interfaces/TimekeepingItemProps";

export default function TimekeepingItem({ index, imageSrc, name, timeIn,timeOut, title,
                                            sx }: Readonly<TimekeepingItemProps>) {
    return (
        <Grid sx={{backgroundColor: index % 2 === 0 ? '#ffffff' : '#ececec',
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            height: 110,
            borderRadius: 2,
            boxShadow: 3,
            px: 2,
            ...sx,}}>
            <Grid size={2}>
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
                            {index}
                        </Typography>
                    </Box>
                    <Box>
                        <img
                            border='1px solid #8a8686'
                            src={imageSrc}
                            alt={name}
                            style={{ width: 74, height: 74, borderRadius: '10%'}}
                        />
                    </Box>
                </Box>
            </Grid>
            <Grid size={5}>
                <Box>
                    <Typography sx={{}} variant="subtitle1" fontSize={24} fontWeight={600}>
                        {name}
                    </Typography>
                    <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                        {title}
                    </Typography>
                </Box>
            </Grid>
            <Grid size={3}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {timeIn}
                </Typography>
            </Grid>
            <Grid size={2}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {timeOut}
                </Typography>
            </Grid>

        </Grid>
    );
};
