package nhanph.timekeeping.processor.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMessageSender {

    private final SimpMessagingTemplate messagingTemplate;

    private int count = 0;

    public ScheduledMessageSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    @Scheduled(fixedRate = 1000) // gửi mỗi 1000ms = 1 giây
    public void sendPeriodicMessage() {
        String message = "Server time message " + count++;
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}

