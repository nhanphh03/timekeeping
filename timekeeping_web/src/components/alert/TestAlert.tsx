import {Box, Grid} from '@mui/material';
import {SnackbarProvider, useSnackbar} from "notistack";
import {TimekeepingItemProps} from "./interfaces/TimekeepingItemProps.ts";
import Button from "@mui/material/Button";
import {handleClickVariant} from "./component/useNotification.tsx";

export default function TestAlert() {
    function AppContent() {
        const { enqueueSnackbar } = useSnackbar();
        const item: TimekeepingItemProps = {
            code: '123',
            name: 'Pham Huu Nhan',
            imageSrc: 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cat_November_2010-1a.jpg/800px-Cat_November_2010-1a.jpg',
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
// function AppContent() {
//         const { enqueueSnackbar } = useSnackbar();
//         const item: TimekeepingItemProps = {
//             code: '123',
//             name: 'Pham Huu Nhan',
//             imageSrc: 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cat_November_2010-1a.jpg/800px-Cat_November_2010-1a.jpg',
//         };

//         return (
//             <Button onClick={handleClickVariant(enqueueSnackbar, item)}>
//                 Trigger Snackbar from Another Component
//             </Button>
//         );
//     }
