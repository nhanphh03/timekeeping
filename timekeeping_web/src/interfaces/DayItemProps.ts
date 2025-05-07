import {SxProps, Theme} from "@mui/material";

export interface DayItemProps {
    child: string;
    sx?: SxProps<Theme>;
    status?: 'active' | 'inactive' | string;
}