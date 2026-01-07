package io.github.elsive2.chatwebsocket.dto.response;

import io.github.elsive2.chatwebsocket.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class MessageDto {
    private UUID messageId;
    private String message;
    private int version;
    private Integer messageChatN;

    public static MessageDto fromEntity(Message message) {
        return new MessageDto(
                message.getId(),
                message.getPayload(),
                message.getVersion(),
                message.getMessageChatN()
        );
    }
}
