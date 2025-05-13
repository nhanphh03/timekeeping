import {Box, Grid, Typography} from '@mui/material';
import {TimekeepingItemProps} from "../../types";
import CircleIcon from '@mui/icons-material/Circle';
import DoneIcon from '@mui/icons-material/Done';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAlt';
import CloseIcon from '@mui/icons-material/Close';
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
            <Grid item xs={4}>
                <Box sx={{ml:4}}>
                    <Typography sx={{}} variant="subtitle1" fontSize={25} fontWeight={600}>
                        {props.name}
                    </Typography>

                    <Box sx={{display: 'flex', alignItems: 'center'}}>
                        <Typography sx={{mr: 2}} variant="body1" fontSize={18} fontWeight={500}>
                            {props.customerCode}
                        </Typography>
                        <Typography sx={{}} variant="body1" fontSize={18} fontWeight={500}>
                            {props.title}
                        </Typography>
                    </Box>
                </Box>
            </Grid>
            <Grid sx={{display: 'flex'}} item xs={2}>
                <CircleIcon sx={{color: '#6d776b', mr: 2, fontSize: 30}}></CircleIcon>
                <Typography sx={{}} variant="body1" fontSize={20} fontWeight={500}>
                    {props.timeIn}
                </Typography>
            </Grid>
            <Grid item xs={2} sx={{display: 'flex'}}>
                <CircleIcon sx={{color: '#2d68c1', mr: 2, fontSize: 30}}></CircleIcon>
                <Typography sx={{}} variant="body1" fontSize={20} fontWeight={500}>
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