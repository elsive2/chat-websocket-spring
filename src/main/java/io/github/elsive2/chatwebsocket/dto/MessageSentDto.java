package io.github.elsive2.chatwebsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MessageSentDto {
    private UUID messageId;
    private String message;
    private int messageChatN;
    private int version;
    private Action action;

    public enum Action {
        CREATED,
        UPDATED
    }
}
