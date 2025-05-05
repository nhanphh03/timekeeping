import React from 'react';
import {Box, Grid, Typography} from '@mui/material';
const TimekeepingItem = ({index, imageSrc, name, time, sx }) => {
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
            <Grid item xs={5}>
                <Box>
                    <Typography sx={{}} variant="subtitle1" fontSize={20} fontWeight={600}>
                        {name}
                    </Typography>
                    {time && (
                        <Typography variant="body2" color="text.secondary">
                            {time}
                        </Typography>
                    )}
                </Box>
            </Grid>
            <Grid item xs={3}>

            </Grid>
            <Grid item xs={2}>

            </Grid>

        </Grid>
    );
};

export default TimekeepingItem;
