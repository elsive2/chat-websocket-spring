package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;
import io.github.elsive2.chatwebsocket.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketService {
    public static final String MESSAGE_DESTINATION = "/queue/messages";

    private final SimpMessagingTemplate messagingTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onMessageEvent(final MessageEvent event) {
        MessageSentDto msgToSend = new MessageSentDto(
                event.messageId(),
                event.payload(),
                event.messageChatN(),
                event.version(),
                event.action()
        );

        log.debug("Publish message to subs - {}", msgToSend);
        messagingTemplate.convertAndSendToUser(
                event.chatId().toString(),
                MESSAGE_DESTINATION,
                msgToSend
        );
    }
}
