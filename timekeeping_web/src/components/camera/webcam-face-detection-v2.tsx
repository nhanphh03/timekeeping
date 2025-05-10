import {useEffect, useRef, useState} from 'react';
import * as faceapi from 'face-api.js';
import * as tf from '@tensorflow/tfjs';
import {Box} from '@mui/material';

export default function WebcamFaceDetectionV2({ sx, videoWidth = 750, videoHeight = 500 }){

    const videoRef = useRef(null);
    const canvasRef = useRef(null);

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

            const resizedResult = result && faceapi.resizeResults(result, displaySize);
            const ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            if (resizedResult) {
                faceapi.draw.drawDetections(canvas, resizedResult);

                const box = resizedResult.box;
                const faceCanvas = document.createElement('canvas');
                faceCanvas.width = box.width;
                faceCanvas.height = box.height;

                // console.log('Detected face:', resizedResult);
                // const base64Image = faceCanvas.toDataURL('image/png');
                // console.log('Cropped Face Base64:', base64Image);
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

