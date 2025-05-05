import {Stack} from "@mui/material";
import DayItem from "./DayItem.js";

function DayItemList() {
    return (
        <Stack direction="row" spacing={1.5} sx={{pt: 1, pl: 1}}>
            <DayItem child = "Monday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Tuesday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Wednesday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Thursday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Friday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Saturday" status = 'active'   sx={{width: '10%', height: 60}}></DayItem>
            <DayItem child = "Sunday" status = 'inActive' sx={{width: '10%', height: 60}}></DayItem>
        </Stack>
    )
}
export default DayItemList;
