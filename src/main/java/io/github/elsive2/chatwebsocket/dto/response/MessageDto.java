package io.github.elsive2.chatwebsocket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class MessageDto {
    private UUID messageId;
    private String message;
    private int version;
}
