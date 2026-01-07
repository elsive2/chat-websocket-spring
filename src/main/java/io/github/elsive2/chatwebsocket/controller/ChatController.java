package io.github.elsive2.chatwebsocket.controller;

import io.github.elsive2.chatwebsocket.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage) {
        log.info("Got message - {}", chatMessage);

        // TODO: Создаем сообщение в базе

        messagingTemplate.convertAndSendToUser(
                chatMessage.getChatId().toString(),
                "/queue/messages",
                chatMessage

        );
    }
}
