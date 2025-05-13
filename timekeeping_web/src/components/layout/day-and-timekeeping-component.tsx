import {Box, Grid} from "@mui/material";
import DayItemList from "../items/day/day-item-list";
import FirstItemTimekeeping from "../items/timekeeping/first-item-timekeeping";
import Stack from "@mui/system/Stack";
import TimekeepingList from "../items/timekeeping/timekeeping-list";
import Footer from "./footer";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
import {CameraConfig, TimekeepingItemProps} from "../types";
import {getAllDetection} from "../service/APIService";
import {Client as StompClient} from "@stomp/stompjs/esm6/client";
// @ts-ignore
import SockJS from "sockjs-client";
import {IMessage, Stomp} from "@stomp/stompjs";
import {DETECTION_API, WEBSOCKET_URL} from "../../../env";
import ConsecutiveSnackbars from "./ConsecutiveSnackbars";

type SetState<T> = React.Dispatch<React.SetStateAction<T>>;

export default function DayAndTimekeepingComponent() {
    const [, setCameraConfig] = useState<CameraConfig | null>(null);
    const [timekeepingList, setTimekeepingList] = useState<TimekeepingItemProps[]>([]);
    const [firstItem, setFirstItem] = useState<TimekeepingItemProps>();
    const stompClientRef = useRef<StompClient | null>(null);

    const initializeStompClient = (setStateCallback: SetState<TimekeepingItemProps[]>) => {
        const socket = new SockJS(WEBSOCKET_URL);
        const stompClient: StompClient = Stomp.over(socket);

        stompClient.onConnect = () => {
            stompClient.subscribe("/topic/timekeeping-detector", (message: IMessage) => {

                const newItems = JSON.parse(message.body);
                if (newItems.length > 0) {
                    setFirstItem(newItems[0]);
                    setStateCallback(newItems.slice(1));
                } else {
                    setFirstItem(null);
                    setStateCallback([]);
                }
            });
        };

        stompClient.activate();
        return stompClient;
    };

    useEffect(() => {
        getAllDetection(`${DETECTION_API}/all`)
            .then(data => {
                if (data.length > 0) {
                    setFirstItem(data[0]);
                    setTimekeepingList(data.slice(1));
                } else {
                    setTimekeepingList([]);
                }
            })
            .catch(err => console.error("Error fetching timekeeping items:", err));
    }, []);

    useEffect(() => {
        stompClientRef.current = initializeStompClient(setTimekeepingList);
        return () => {
            if (stompClientRef.current && stompClientRef.current.connected) {
                stompClientRef.current.deactivate().then(() => console.log("WebSocket disconnected"));
            }
        };
    }, []);

    return (
        <Grid sx={{height: "100%"}}>
            <Box sx={{height: "1%", width: "100%"}}></Box>
            <Box sx={{height: "10%", width: "100%"}}>
                <ConsecutiveSnackbars/>
                <DayItemList/>
            </Box>
            <Box sx={{height: "10%", width: "100%"}}>
                <FirstItemTimekeeping {...firstItem} />
            </Box>
            <Box sx={{height: "2%", width: "100%"}}></Box>
            <Box sx={{height: "68%", width: "100%", overflowY: "auto"}}>
                <Stack spacing={2} sx={{}}>
                    <TimekeepingList items={timekeepingList}/>
                </Stack>
            </Box>
            <Box sx={{height: "1%", width: "100%"}}></Box>
            <Box sx={{height: "7%", width: "98%", ml: 2}}>
                <Footer onSave={setCameraConfig}/>
            </Box>
        </Grid>
    );
}