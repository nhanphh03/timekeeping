import { Box } from '@mui/material';
import { useEffect, useRef, useState, useMemo } from "react";
import * as faceapi from "face-api.js";
import * as tf from "@tensorflow/tfjs";
import {CameraConfigProps} from "../../interfaces/CameraConfigProps";

export default function WebcamFaceDetection(props: Readonly<CameraConfigProps>) {
    const videoRef = useRef<HTMLVideoElement>(null);
    const canvasRef = useRef<HTMLCanvasElement>(null);

    const [selectedFaceDetector, setSelectedFaceDetector] = useState('ssd_mobilenetv1');
    const [inputSize, setInputSize] = useState(512);
    const [minConfidence, setMinConfidence] = useState(0.5);
    const [scoreThreshold, setScoreThreshold] = useState(0.5);

    // Cập nhật lại state mỗi khi cameraConfig thay đổi
    useEffect(() => {
        if (props.cameraConfig) {
            setSelectedFaceDetector(props.cameraConfig.modelFaceDetector || 'ssd_mobilenetv1');
            setInputSize(props.cameraConfig.inputSize || 512);
            setMinConfidence(props.cameraConfig.minConfidence || 0.5);
            setScoreThreshold(props.cameraConfig.scoreThreshold || 0.5);

            console.log("Cập nhật cameraConfig:", props.cameraConfig);
        }
    }, [props.cameraConfig]);

    // Dùng useMemo để tối ưu hàm tạo options
    const faceDetectorOptions = useMemo(() => {
        return selectedFaceDetector === 'ssd_mobilenetv1'
            ? new faceapi.SsdMobilenetv1Options({ minConfidence })
            : new faceapi.TinyFaceDetectorOptions({ inputSize, scoreThreshold });
    }, [selectedFaceDetector, minConfidence, inputSize, scoreThreshold]);

    useEffect(() => {
        const loadModels = async () => {
            const MODEL_URL = '/models';

            try {
                await tf.setBackend('webgl');
                await tf.ready();

                await Promise.all([
                    faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL),
                    faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL),
                ]);

                await startVideo();
            } catch (err) {
                console.error('Lỗi khi tải model hoặc TensorFlow backend:', err);
            }
        };

        const startVideo = async () => {
            try {
                const stream = await navigator.mediaDevices.getUserMedia({ video: {} });
                if (videoRef.current) {
                    videoRef.current.srcObject = stream;
                }
            } catch (err) {
                console.error('Không thể truy cập webcam:', err);
            }
        };

        loadModels().then(r => console.debug(""));
    }, []);

    // Khi cameraConfig thay đổi, phát lại video để gọi lại onPlay
    useEffect(() => {
        if (videoRef.current && !videoRef.current.paused) {
            videoRef.current.pause();
            videoRef.current.play().then(r => console.debug(""));
        }
    }, [selectedFaceDetector, inputSize, minConfidence, scoreThreshold]);

    const handleVideoOnPlay = async () => {
        const video = videoRef.current;
        const canvas = canvasRef.current;

        if (!video || !canvas) return;

        const displaySize = { width: video.videoWidth, height: video.videoHeight };
        canvas.width = displaySize.width;
        canvas.height = displaySize.height;
        faceapi.matchDimensions(canvas, displaySize);

        const detect = async () => {
            if (video.paused || video.ended) return;

            const result = await faceapi.detectSingleFace(video, faceDetectorOptions);
            const resizedResult = result && faceapi.resizeResults(result, displaySize);

            const ctx = canvas.getContext('2d');
            if (ctx) {
                ctx.clearRect(0, 0, canvas.width, canvas.height);
            }

            if (resizedResult) {
                faceapi.draw.drawDetections(canvas, resizedResult);
            }

            requestAnimationFrame(detect);
        };

        await detect();
    };

    return (
        <Box sx={{ ...props.sx }}>
            <div style={{ position: 'relative' }}>
                <video
                    ref={videoRef}
                    autoPlay
                    muted
                    onPlay={handleVideoOnPlay}
                    style={{
                        width: props.videoWidth,
                        height: props.videoHeight,
                        objectFit: 'cover',
                        borderRadius: 10
                    }}
                />
                <canvas
                    ref={canvasRef}
                    style={{
                        position: 'absolute',
                        top: 0,
                        left: 0,
                    }}
                />
            </div>
        </Box>
    );
}
