package io.github.elsive2.chatwebsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ChatMessageDto {
    private UUID chatId;
    private UUID userId;
    private String message;
}
