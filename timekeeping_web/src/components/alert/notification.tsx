import { EnqueueSnackbar } from 'notistack';
import { Box, Typography } from '@mui/material';
import {TimekeepingItemProps} from "../types";

export const handleClickVariant = (
    enqueueSnackbar: EnqueueSnackbar,
    item?: TimekeepingItemProps
) => () => {
    enqueueSnackbar(
        <Box display="flex" alignItems="center">
            <img
                src={item?.imageSrc}
                alt="example"
                style={{ width: 60, height: 60, borderRadius: '50%', marginRight: '20px' }}
            />
            <Typography sx={{ marginRight: 8, display: 'flex' }}>
                Welcome
                <Typography sx={{ fontWeight: 600, mr: 0.5, ml: 0.5 }}>
                    {item?.customerCode}
                </Typography>
                {item?.name}
            </Typography>
        </Box>,
        {
            autoHideDuration: 2000,
            anchorOrigin: {
                vertical: 'top',
                horizontal: 'right',
            },
            style: {
                backgroundColor: '#69ba6c',
                width: '400px',
                minWidth: '200px',
                height: '70px',
                fontSize: '21px',
            },
        }
    );
};
