package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;
import io.github.elsive2.chatwebsocket.entity.Message;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketService {
    public static final String MESSAGE_DESTINATION = "/queue/messages";

    private final SimpMessagingTemplate messagingTemplate;

    public void sendChatMessage(final Message message, final MessageSentDto.Action action) {
        MessageSentDto msgToSend = new MessageSentDto(message.getId(), message.getPayload(), action);

        log.debug("Publish message to subs - {}", msgToSend);
        messagingTemplate.convertAndSendToUser(
                message.getChat().getId().toString(),
                MESSAGE_DESTINATION,
                msgToSend
        );
    }
}
