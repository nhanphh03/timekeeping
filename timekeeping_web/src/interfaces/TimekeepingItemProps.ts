import {ReactNode} from "react";
import {SxProps, Theme} from "@mui/material";

export interface TimekeepingItemProps {
    imageSrc: string;
    name: string;
    timeIn: string;
    timeOut: string;
    title: string;
    index?: ReactNode;
    sx?: SxProps<Theme>;
}