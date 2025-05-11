import {Stack} from '@mui/material';
import TimekeepingItem from './timekeeping-item';
import {TimekeepingItemProps} from "../../types";
import {useEffect, useState} from "react";
import {getAllDetection} from "../../service/APIService";

export default function TimekeepingList() {

    const [data, setData] = useState<TimekeepingItemProps[]>([]);
    useEffect(() => {
        getAllDetection('http://localhost:8080/api/detection/all')
            .then(setData)
            .catch((err) => console.error('Error fetching timekeeping data:', err));
    }, []);
    return (
        <Stack spacing={1} sx={{ p: 2 }}>
            {data.map((item, idx) => (
                <TimekeepingItem
                    key={idx}
                    {...item}
                    index={String(idx + 1).padStart(2, '0')}
                />
            ))}
        </Stack>
    );
}
