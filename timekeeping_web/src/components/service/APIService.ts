import {TimekeepingItemProps} from "../types";

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

export async function getAllDetection(url: string): Promise<TimekeepingItemProps[]> {
    let res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    if (!res.ok) throw new Error('Network response was not ok');
    return res.json();
}

export function firstDetection(url: string): Promise<TimekeepingItemProps> {
    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((res) => {
        if (!res.ok) throw new Error('Network response was not ok');
        return res.json();
    });
}