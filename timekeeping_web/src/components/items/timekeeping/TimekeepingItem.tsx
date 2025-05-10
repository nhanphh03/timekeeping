import {Box, Grid, Typography} from '@mui/material';
import {TimekeepingItemProps} from "../../../interfaces/TimekeepingItemProps";

export default function TimekeepingItem(props: Readonly<TimekeepingItemProps>) {
    return (
        <Grid container sx={{
            backgroundColor: props.index as number % 2 === 0 ? '#ffffff' : '#ececec',
            display: 'flex',
            alignItems: 'center',
            height: 110,
            borderRadius: 2,
            boxShadow: 3,
            px: 2,
            ...props.sx,
        }}>
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
                            src={props.imageSrc}
                            alt={props.name}
                            style={{
                                width: 74,
                                height: 74,
                                border: '1px solid #8a8686',
                                borderRadius: '10%'
                            }}
                        />
                    </Box>
                </Box>
            </Grid>
            <Grid item xs={5}>
                <Box sx={{ml:4}}>
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