import {CameraConfig} from "./CameraConfig";
import {SxProps, Theme} from "@mui/material";

export interface CameraConfigProps {
    sx?: SxProps<Theme>;
    onSave?: (config: CameraConfig) => void;
    cameraConfig: CameraConfig | null;
}
