// import React, { useEffect, useRef, useState } from 'react';
// import * as faceapi from 'face-api.js';
// import * as tf from '@tensorflow/tfjs'; // thêm import TensorFlow
//
// const WebcamFaceDetectionV3 = () => {
//   const videoRef = useRef(null);
//   const canvasRef = useRef(null);
//
//   const [selectedFaceDetector, setSelectedFaceDetector] = useState('ssd_mobilenetv1');
//   const [inputSize, setInputSize] = useState(512);
//   const [minConfidence, setMinConfidence] = useState(0.5);
//   const [scoreThreshold, setScoreThreshold] = useState(0.5);
//
//   useEffect(() => {
//     const loadModels = async () => {
//       const MODEL_URL = '/models';
//
//       try {
//         await tf.setBackend('webgl');
//         await tf.ready();
//
//         await Promise.all([
//           faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL),
//           faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL),
//         ]);
//
//         await startVideo();
//       } catch (err) {
//         console.error('Lỗi khi tải model hoặc TensorFlow backend:', err);
//       }
//     };
//
//     const startVideo = async () => {
//       try {
//         videoRef.current.srcObject = await navigator.mediaDevices.getUserMedia({ video: {} });
//       } catch (err) {
//         console.error('Không thể truy cập webcam:', err);
//       }
//     };
//
//     loadModels().then(r => console.debug(""));
//   }, []);
//
//   const getFaceDetectorOptions = () => {
//     return selectedFaceDetector === 'ssd_mobilenetv1'
//         ? new faceapi.SsdMobilenetv1Options({ minConfidence })
//         : new faceapi.TinyFaceDetectorOptions({ inputSize, scoreThreshold });
//   };
//
//   const handleVideoOnPlay = async () => {
//     const video = videoRef.current;
//     const canvas = canvasRef.current;
//
//     if (!video || !canvas) return;
//
//     const displaySize = { width: video.videoWidth, height: video.videoHeight };
//     canvas.width = displaySize.width;
//     canvas.height = displaySize.height;
//     faceapi.matchDimensions(canvas, displaySize);
//
//     const detect = async () => {
//       if (video.paused || video.ended) return;
//
//       const options = getFaceDetectorOptions();
//       const result = await faceapi.detectSingleFace(video, options);
//
//       const resizedResult = result && faceapi.resizeResults(result, displaySize);
//       const ctx = canvas.getContext('2d');
//       ctx.clearRect(0, 0, canvas.width, canvas.height);
//
//       if (resizedResult) {
//         faceapi.draw.drawDetections(canvas, resizedResult);
//
//         const box = resizedResult.box;
//         const faceCanvas = document.createElement('canvas');
//         faceCanvas.width = box.width;
//         faceCanvas.height = box.height;
//
//         // console.log('Detected face:', resizedResult);
//         // const base64Image = faceCanvas.toDataURL('image/png');
//         // console.log('Cropped Face Base64:', base64Image);
//       }
//
//       requestAnimationFrame(detect);
//     };
//
//     await detect();
//   };
//
//   return (
//       <div>
//         <div style={{ position: 'relative' }}>
//           <video
//               ref={videoRef}
//               autoPlay
//               muted
//               onPlay={handleVideoOnPlay}
//               style={{ width: '720px', height: '560px' }}
//           />
//           <canvas
//               ref={canvasRef}
//               style={{ position: 'absolute', top: 0, left: 0 }}
//           />
//         </div>
//
//         <div hidden={true}>
//           <label>Select Face Detector:</label>
//           <select onChange={(e) => setSelectedFaceDetector(e.target.value)} value={selectedFaceDetector}>
//             <option value="ssd_mobilenetv1">SSD Mobilenet V1</option>
//             <option value="tiny_face_detector">Tiny Face Detector</option>
//           </select>
//         </div>
//
//         {selectedFaceDetector === 'ssd_mobilenetv1' && (
//             <div hidden={true}>
//               <label>Min Confidence:</label>
//               <button onClick={() => setMinConfidence((prev) => Math.max(prev - 0.1, 0.1))}>-</button>
//               <span>{minConfidence.toFixed(2)}</span>
//               <button onClick={() => setMinConfidence((prev) => Math.min(prev + 0.1, 1.0))}>+</button>
//             </div>
//         )}
//
//         {selectedFaceDetector === 'tiny_face_detector' && (
//             <div>
//               <label>Input Size:</label>
//               <select onChange={(e) => setInputSize(Number(e.target.value))} value={inputSize}>
//                 {[128, 160, 224, 320, 416, 512, 608].map(size => (
//                     <option key={size} value={size}>{`${size} x ${size}`}</option>
//                 ))}
//               </select>
//               <label>Score Threshold:</label>
//               <button onClick={() => setScoreThreshold((prev) => Math.max(prev - 0.1, 0.1))}>-</button>
//               <span>{scoreThreshold.toFixed(2)}</span>
//               <button onClick={() => setScoreThreshold((prev) => Math.min(prev + 0.1, 1.0))}>+</button>
//             </div>
//         )}
//       </div>
//   );
// };
//
// export default WebcamFaceDetectionV3;
