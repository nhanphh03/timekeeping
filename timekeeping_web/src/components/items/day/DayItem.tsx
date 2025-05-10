import {Box, Grid, Typography} from '@mui/material';
import {DayItemProps} from "../../../interfaces/DayItemProps";

export default function DayItem(props: Readonly<DayItemProps>) {
    return (
        <Grid container sx={{
            backgroundColor: props.status === 'active' ? '#b3b3b3' : '#ffffff',
            textAlign: 'center',
            borderRadius: 2,
            boxShadow: 3,
            px: 2,
            py: 1,
            ...props.sx,
        }}>
            <Grid item xs={7} sx={{}}>
                <Box sx={{}}>
                    <Typography fontWeight={500}
                                fontSize={19}>
                        {props.child}
                    </Typography>
                    <Typography fontFamily={""}
                                fontWeight={500}
                                fontSize={18}>
                        {props.month}
                    </Typography>
                </Box>
            </Grid>
            <Grid item xs={5}>
                <Box sx={{mt: 3, ml: 2}}>
                    <Typography fontFamily={"Roboto"}
                                fontWeight={700}
                                fontStyle={"italic"}
                                fontSize={25}
                                sx={{color: '#000000'}}>
                        {props.day}
                    </Typography>
                </Box>
            </Grid>
        </Grid>
    );
};
