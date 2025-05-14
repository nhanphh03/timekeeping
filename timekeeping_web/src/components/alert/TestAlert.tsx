import {Box, Grid} from '@mui/material';
import {SnackbarProvider, useSnackbar} from "notistack";
import Button from "@mui/material/Button";
import {handleClickVariant} from "./notification";
import {TimekeepingItemProps} from "../types";

export default function TestAlert() {
    function AppContent() {
        const { enqueueSnackbar } = useSnackbar();
        const item: TimekeepingItemProps = {
            customerCode: '123',
            name: 'Pham Huu Nhan',
            imageSrc: 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cat_November_2010-1a.jpg/800px-Cat_November_2010-1a.jpg',
            description: 'Sample description',
            status: 'active',
            timeIn: '09:00 AM',
            timeOut: '05:00 PM',
            title: 'Sample title',
        };

        return (
            <Button onClick={handleClickVariant(enqueueSnackbar, item)}>
                Trigger Snackbar from Another Component
            </Button>
        );
    }
    return (
        <Grid container sx={{height: '100vh'}}>
            <Box>
                <SnackbarProvider maxSnack={9}>
                    <AppContent />
                </SnackbarProvider>
            </Box>
        </Grid>
    );
}



// Cách dùng 
