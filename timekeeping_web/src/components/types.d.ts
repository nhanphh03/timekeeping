import {SxProps, Theme} from "@mui/material";
import {ReactNode} from "react";

export interface CameraConfig {
  modelFaceDetector: string;
  minConfidence?: number;
  inputSize?: number;
  scoreThreshold?: number;
}

export interface CameraConfigProps {
  sx?: SxProps<Theme>;
  onSave?: (config: CameraConfig) => void;
  cameraConfig?: CameraConfig | null;
  videoWidth?: number;
  videoHeight?: number;
}

export interface DayItemProps {
  child: string
  month?: string;
  day?: string;
  sx?: SxProps<Theme>;
  status?: 'active' | 'inactive' | string;
}

export interface TimekeepingItemProps {
  imageSrc: string;
  name: string;
  timeIn: string;
  timeOut: string;
  title: string;
  index?: ReactNode;
  sx?: SxProps<Theme>;
}