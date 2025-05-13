import * as React from 'react';
import Button from '@mui/material/Button';
import { SnackbarProvider, VariantType, useSnackbar } from 'notistack';

function MyApp() {
    const { enqueueSnackbar } = useSnackbar();

    const handleClickVariant = (variant: VariantType) => () => {
        enqueueSnackbar('This is a success message!', { variant });
    };

    return (
        <React.Fragment>
            <Button onClick={handleClickVariant('success')}>Show success snackbar</Button>
        </React.Fragment>
    );
}

export default function IntegrationNotistack() {
    return (
        <SnackbarProvider maxSnack={3}>
            <MyApp />
        </SnackbarProvider>
    );
}
