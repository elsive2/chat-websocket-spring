package io.github.elsive2.chatwebsocket.controller;

import io.github.elsive2.chatwebsocket.dto.ChatMessageDto;
import io.github.elsive2.chatwebsocket.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public final class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload @Valid final ChatMessageDto payload) {
        chatService.processMessage(payload);
    }
}
