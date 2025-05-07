import {Stack} from '@mui/material';
import TimekeepingItem from './TimekeepingItem.js';

export default function TimekeepingList() {
    return (
        <Stack spacing={1} sx={{p: 2}}>
            <TimekeepingItem
                index={'02'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                timeIn="08:00 - 17:00"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'03'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'04'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'05'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'06'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'07'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'08'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'09'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
            <TimekeepingItem
                index={'10'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Trần Thị B"
                timeIn="08:15 - 17:05"
                timeOut="08:15 - 17:05"
                title="Lotte Finance"
            />
        </Stack>
    );
}