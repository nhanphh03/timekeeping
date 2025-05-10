import {Stack} from "@mui/material";
import DayItem from "./day-item.js";

export default function DayItemList() {
    return (
        <Stack direction="row" spacing={1.5} sx={{pt: 1, pl: 1}}>
            <DayItem child = "Monday" status = 'inActive' day= '05' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Tuesday" status = 'inActive' day= '06' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Wednesday" status = 'inActive' day= '07' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Thursday" status = 'inActive' day= '08' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Friday" status = 'inActive' day= '09' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Saturday" status = 'active' day= '10' month= 'May'   sx={{width: '13%', height: 75}}></DayItem>
            <DayItem child = "Sunday" status = 'inActive' day= '11' month= 'May' sx={{width: '13%', height: 75}}></DayItem>
        </Stack>
    )
}
