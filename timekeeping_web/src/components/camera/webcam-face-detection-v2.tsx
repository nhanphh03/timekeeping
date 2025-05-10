import {useEffect, useRef, useState} from 'react';
import * as faceapi from 'face-api.js';
import * as tf from '@tensorflow/tfjs';
import {Box} from '@mui/material';
import {sendDetectFaceImgBase64} from "../service/APIService";
import { v4 as uuidv4 } from 'uuid';

export default function WebcamFaceDetectionV2({ sx, videoWidth = 750, videoHeight = 500 }){

    const videoRef = useRef(null);
    const canvasRef = useRef(null);

    const id = uuidv4();
    const [selectedFaceDetector] = useState('ssd_mobilenetv1');
    const [inputSize] = useState(512);
    const [minConfidence] = useState(0.5);
    const [scoreThreshold] = useState(0.5);

    useEffect(() => {
        const loadModels = async () => {
            const MODEL_URL = '/models';

            try {
                // Khởi tạo backend TensorFlow trước khi dùng face-api
                await tf.setBackend('webgl'); // hoặc 'cpu' nếu muốn
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
                videoRef.current.srcObject = await navigator.mediaDevices.getUserMedia({video: {}});
            } catch (err) {
                console.error('Không thể truy cập webcam:', err);
            }
        };

        loadModels();
    }, []);

    const getFaceDetectorOptions = () => {
        return selectedFaceDetector === 'ssd_mobilenetv1'
            ? new faceapi.SsdMobilenetv1Options({minConfidence})
            : new faceapi.TinyFaceDetectorOptions({inputSize, scoreThreshold});
    };

    const handleVideoOnPlay = async () => {
        const video = videoRef.current;
        const canvas = canvasRef.current;

        if (!video || !canvas) return;

        const displaySize = {width: video.videoWidth, height: video.videoHeight};
        canvas.width = displaySize.width;
        canvas.height = displaySize.height;
        faceapi.matchDimensions(canvas, displaySize);

        const detect = async () => {
            if (video.paused || video.ended) return;

            const options = getFaceDetectorOptions();
            const result = await faceapi.detectSingleFace(video, options);

            // Kiểm tra xem có phát hiện khuôn mặt hay không
            if (!result) {
                requestAnimationFrame(detect);
                return;
            }

            const resizedResult = faceapi.resizeResults(result, displaySize);
            const ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            if (resizedResult) {
                // Vẽ kết quả phát hiện lên canvas
                faceapi.draw.drawDetections(canvas, resizedResult);

                const box = resizedResult.box;

                // Tạo canvas mới để chứa phần khuôn mặt
                const faceCanvas = document.createElement('canvas');
                faceCanvas.width = box.width;
                faceCanvas.height = box.height;

                const faceCtx = faceCanvas.getContext('2d');
                if (!faceCtx) {
                    console.error('Cannot get canvas 2D context');
                    return;
                }

                // Cắt phần khuôn mặt từ canvas gốc và vẽ vào faceCanvas
                faceCtx.drawImage(
                    video, // Dùng video trực tiếp thay vì originalCanvas
                    box.x, box.y, box.width, box.height, // Phần cần cắt
                    0, 0, box.width, box.height // Vị trí vẽ lên faceCanvas
                );

                // Chuyển ảnh thành base64
                const base64Image = faceCanvas.toDataURL('image/png');

                const body = {
                    base64_image: base64Image,
                    request_id: uuidv4(),
                    time_request: new Date().getTime(),
                    camera_code: 'LAPTOP_LENOVO'
                };

                sendDetectFaceImgBase64("http://localhost:2121/procedure/send-detector", body);
            }

            requestAnimationFrame(detect);
        };

        detect();
    };

    return (
        <Box sx={{...sx}}>
            <div>
                <video
                    ref={videoRef}
                    autoPlay
                    muted
                    onPlay={handleVideoOnPlay}
                    style={{
                        width: videoWidth,
                        height: videoHeight,
                        objectFit: 'cover',
                        borderRadius: 10
                    }}
                />

                <canvas
                    ref={canvasRef}
                    style={{position: 'absolute', top: 28, left: 59 }}
                />
            </div>
        </Box>
    );
};

