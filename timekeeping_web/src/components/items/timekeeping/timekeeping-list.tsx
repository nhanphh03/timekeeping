import {Stack} from '@mui/material';
import TimekeepingItem from './timekeeping-item';

export default function TimekeepingList() {
    return (
        <Stack spacing={1} sx={{p: 2}}>
            <TimekeepingItem
                index={'02'}
                status='2'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"

                description="Welcome"
            />
            <TimekeepingItem
                index={'03'}
                status='1'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"
                description="Welcome"
            />
            <TimekeepingItem
                index={'04'}
                status='2'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"
                description="Welcome"
            />
            <TimekeepingItem
                index={'05'}
                status='1'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"
                description="Welcome"
            />
            <TimekeepingItem
                index={'06'}
                status='1'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"
                description="Welcome"
            />
            <TimekeepingItem
                index={'07'}
                status='0'
                imageSrc="http://100.83.174.102:8181/api/v1/file/media/avatar/2003"
                name="Nguyễn Văn A"
                title="Nhan"
                timeIn="08:00:00"
                timeOut="17:00:00"
                description="Insufficient"
            />
        </Stack>
    );
}
