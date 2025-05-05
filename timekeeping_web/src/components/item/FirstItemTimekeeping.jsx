import React from 'react';
import {Box, Grid, Typography} from '@mui/material';
const FirstItemTimekeeping = ({imageSrc, name, timeIn, timeOut, title, sx }) => {
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
            ...sx,}}>
            <Grid item xs={1}>
                <Box sx={{display: 'flex', ml: 2}}>
                    <Box>
                        <img
                            border='1px solid #8b8b8b'
                            src={imageSrc}
                            alt={name}
                            style={{ width: 74,
                                height: 74,
                                borderRadius: '10%',
                                boxShadow: 3}}
                        />
                    </Box>
                </Box>
            </Grid>
            <Grid item xs={6}>
                <Box sx={{ml: 6}}>
                    <Typography sx={{}} variant="subtitle1" fontSize={24} fontWeight={600}>
                        {name}
                    </Typography>
                    <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                        {title}
                    </Typography>
                </Box>
            </Grid>
            <Grid item xs={3}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {timeIn}
                </Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                    {timeOut}
                </Typography>
            </Grid>

        </Grid>
    );
};

export default FirstItemTimekeeping;
