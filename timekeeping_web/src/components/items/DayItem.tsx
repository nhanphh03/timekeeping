import { Box, Typography } from '@mui/material';
import {DayItemProps} from "../../interfaces/DayItemProps.ts";

export default function DayItem(props: Readonly<DayItemProps>) {
    return (
        <Box
            sx={{
                backgroundColor: props.status === 'active' ? '#00bcd4' : '#ffffff',
                textAlign: 'center',
                borderRadius: 2,
                boxShadow: 3,
                px: 2,
                py: 1,
                ...props.sx,
            }}
        >
            <Typography variant="body1" fontWeight={500}>
                {props.child}
            </Typography>
            <Typography variant="body1" fontWeight={500}>
                03/05/2025
            </Typography>
        </Box>
    );
};
