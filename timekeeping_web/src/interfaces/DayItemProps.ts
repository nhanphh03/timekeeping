import {SxProps, Theme} from "@mui/material";

export interface DayItemProps {
    child: string
    month?: string;
    day?: string;
    sx?: SxProps<Theme>;
    status?: 'active' | 'inactive' | string;
}