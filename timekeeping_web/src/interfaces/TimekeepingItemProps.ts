import {ReactNode} from "react";
import {SxProps, Theme} from "@mui/material";
// Add this at the top of the ESLint configuration file (e.g., .eslintrc.json or .eslintrc.js):
// Ensure ESLint parses TypeScript files correctly
export interface TimekeepingItemProps {
    imageSrc: string;
    name: string;
    timeIn: string;
    timeOut: string;
    title: string;
    index?: ReactNode;
    sx?: SxProps<Theme>;
}