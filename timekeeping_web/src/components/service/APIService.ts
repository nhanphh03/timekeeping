export function sendDetectFaceImgBase64(url: string, data: any): void {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).catch((error) => {
        console.error('Failed to send detect face image:', error);
    });
}
