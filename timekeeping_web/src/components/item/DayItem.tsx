import { Box, Typography, SxProps, Theme } from '@mui/material';
import * as React from "react";

interface DayItemProps {
    child: string;
    sx?: SxProps<Theme>;
    status?: 'active' | 'inactive' | string;
}

function DayItem(props: Readonly<DayItemProps>) {
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

export default DayItem;
