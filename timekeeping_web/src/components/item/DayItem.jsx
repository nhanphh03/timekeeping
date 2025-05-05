import { Box, Typography, SxProps, Theme } from '@mui/material';
import React, { ReactNode } from 'react';

interface DayItemProps {
    children: ReactNode;
    sx?: SxProps<Theme>;
    status?: 'active' | 'inactive' | string;
}

const DayItem: React.FC<DayItemProps> = ({ children, sx, status }) => {
    return (
        <Box
            sx={{
                backgroundColor: status === 'active' ? '#00bcd4' : '#ffffff',
                textAlign: 'center',
                borderRadius: 2,
                boxShadow: 3,
                px: 2,
                py: 1,
                ...sx,
            }}
        >
            <Typography variant="body1" fontWeight={500}>
                {children}
            </Typography>
            <Typography variant="body1" fontWeight={500}>
                03/05/2025
            </Typography>
        </Box>
    );
};

export default DayItem;
