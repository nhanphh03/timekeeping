import {Stack} from '@mui/material';
import TimekeepingItem from './TimekeepingItem';

export default function TimekeepingList() {
    return (
        <Stack spacing={1} sx={{p: 2}}>
            <TimekeepingItem
                index={'01'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'02'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'03'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'04'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'05'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'06'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'07'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'08'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
            <TimekeepingItem
                index={'09'}
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                title="Nhan"
                name="Trần Thị B"
                timeOut="08:00 - 17:00"
                timeIn="08:00 - 17:00"
            />
        </Stack>
    );
}
