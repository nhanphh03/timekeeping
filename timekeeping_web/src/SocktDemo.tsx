//
// import {useEffect, useRef, useState} from "react";
// import SockJS from 'sockjs-client';
// import { Client as StompClient, IMessage, Stomp } from '@stomp/stompjs';
// import {TimekeepingItemProps} from "./components/types";
//
//
// export default function WebSocketDemo(){
//     const [messages, setMessages] = useState<TimekeepingItemProps[]>([]);
//     const clientRef = useRef<StompClient | null>(null);
//
//     useEffect(() => {
//         const socket = new SockJS('http://localhost:8080/ws');
//         const client: StompClient = Stomp.over(socket);
//
//         client.onConnect = () => {
//             client.subscribe('/topic/timekeeping-detector', (message: IMessage) => {
//                 setMessages([]);
//                 setMessages(JSON.parse(message.body));
//             });
//         };
//         client.activate();
//
//         clientRef.current = client;
//
//         return () => {
//             if (clientRef.current && clientRef.current.connected) {
//                 clientRef.current.deactivate().then(() => {
//                     console.log('Disconnected');
//                 });
//             }
//         };
//     }, []);
//
//     const sendMessage = () => {
//         if (clientRef.current && clientRef.current.connected) {
//             clientRef.current.publish({ destination: '/app/chat', body: 'Hello from React TS!' });
//         }
//     };
//
//     return (
//         <div>
//             <button onClick={sendMessage}>Send Message</button>
//             <h3>Messages:</h3>
//             <ul>
//                 {messages.map((m, i) => (
//                     <li key={i}>{m}</li>
//                 ))}
//             </ul>
//         </div>
//     );
// };
//
