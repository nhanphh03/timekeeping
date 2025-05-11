import {Box, Grid, Typography} from '@mui/material';
import {TimekeepingItemProps} from "../../types";
import SentimentSatisfiedAltIcon from "@mui/icons-material/SentimentSatisfiedAlt";
import DoneIcon from "@mui/icons-material/Done";
import CloseIcon from "@mui/icons-material/Close";

export default function FirstItemTimekeeping(props: Readonly<TimekeepingItemProps>) {

    return (
        <Grid sx={{
            backgroundColor: '#e4fcff',
            display: 'flex',
            alignItems: 'center',
            gap: 2,
            height: 110,
            borderRadius: 2,
            px: 2,
            width: '98%',
            ml: 1,
            boxShadow: 3,
            ...props.sx,
        }}>
            <Grid xs={2} sx={{display: 'flex'}}>
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
                <Box sx={{display: 'flex', ml: 2}}>
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
            <Grid xs={4}>
                <Box sx={{ml: 6}}>
                    <Typography sx={{}} variant="subtitle1" fontSize={24} fontWeight={600}>
                        {props.name}
                    </Typography>
                    <Typography sx={{}} variant="body1" fontSize={15} fontWeight={500}>
                        {props.title}
                    </Typography>
                </Box>
            </Grid>
            <Grid sx={{display: 'flex'}} xs={2}>
                <Typography
                    sx={{
                        height: 40,
                        width: 40,
                        backgroundColor: 'green',
                        mr: 2,
                        borderRadius: '50%',
                        color: 'white',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        fontSize: 15,
                        fontWeight: 'bold'
                    }}
                >
                    IN
                </Typography>
                <Typography sx={{mt:0.5}} variant="body1" fontSize={20} fontWeight={500}>
                    {props.timeIn}
                </Typography>
            </Grid>
            <Grid sx={{display: 'flex'}} xs={2}>
                <Typography
                    sx={{
                        height: 40,
                        width: 40,
                        backgroundColor: 'red',
                        mr: 2,
                        borderRadius: '50%',
                        color: 'white',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        fontSize: 15,
                        fontWeight: 'bold'
                    }}
                >
                    OUT
                </Typography>

                <Typography sx={{mt:0.5}} variant="body1" fontSize={20} fontWeight={500}>
                    {props.timeOut}
                </Typography>
            </Grid>
            <Grid item xs={2} sx={{ display: 'flex', alignItems: 'center' }}>
                {props.status === '1' && (
                    <SentimentSatisfiedAltIcon sx={{ color: 'blue', mr: 2, fontSize: 30 }} />
                )}
                {props.status === '2' && (
                    <DoneIcon sx={{ color: '#62bc50', mr: 2, fontSize: 30 }} />
                )}
                {props.status === '0' && (
                    <CloseIcon sx={{ color: '#cc3737', mr: 2, fontSize: 30 }} />
                )}
                <Typography variant="body1" fontSize={20} fontWeight={500}>
                    {props.description}
                </Typography>
            </Grid>


        </Grid>
    );
};
