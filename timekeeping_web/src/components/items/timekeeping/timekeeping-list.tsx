import { Stack } from '@mui/material';
import TimekeepingItem from './timekeeping-item';
import { TimekeepingItemProps } from "../../types";

interface TimekeepingListProps {
    items: TimekeepingItemProps[];
}

export default function TimekeepingList({ items }: Readonly<TimekeepingListProps>) {
    return (
        <Stack spacing={1} sx={{ p: 2 }}>
            {items.map((item, idx) => (
                <TimekeepingItem
                    key={idx}
                    {...item}
                    index={String(idx + 1).padStart(2, '0')}
                />
            ))}
        </Stack>
    );
}
