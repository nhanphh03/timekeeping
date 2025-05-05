import {Stack} from "@mui/material";
import DayItem from "./DayItem.jsx";

function DayItemList() {
    return (
        <Stack direction="row" spacing={1.5} sx={{pt: 1, pl: 1}}>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Monday</DayItem>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Tuesday</DayItem>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Wednesday</DayItem>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Thursday</DayItem>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Friday</DayItem>
            <DayItem status = 'active'   sx={{width: '10%', height: 60}}>Saturday</DayItem>
            <DayItem status = 'inActive' sx={{width: '10%', height: 60}}>Sunday</DayItem>
        </Stack>
    )
}
export default DayItemList;
