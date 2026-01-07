package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;
import io.github.elsive2.chatwebsocket.dto.request.ChatMessageDto;
import io.github.elsive2.chatwebsocket.entity.Chat;
import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.entity.User;
import io.github.elsive2.chatwebsocket.event.MessageEvent;
import io.github.elsive2.chatwebsocket.repostiory.ChatRepository;
import io.github.elsive2.chatwebsocket.repostiory.MessageRepository;
import io.github.elsive2.chatwebsocket.repostiory.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void sendMessage(final @Valid ChatMessageDto payload) {
        log.debug("Got message - {}", payload);

        Chat chat = chatRepository.findByIdRequired(payload.getChatId());
        User user = userRepository.findByIdRequired(payload.getUserId());

        Integer messageChatN = chatRepository.nextMessageChatN(payload.getChatId());
        if (messageChatN == null) {
            throw new IllegalStateException("Failed to increment message counter for chat " + payload.getChatId());
        }

        Message message = new Message(user, chat, payload.getMessage(), messageChatN);

        log.debug("Saving message - {}", message);
        messageRepository.saveAndFlush(message);

        eventPublisher.publishEvent(new MessageEvent(
                message.getId(),
                chat.getId(),
                message.getPayload(),
                message.getMessageChatN(),
                message.getVersion(),
                MessageSentDto.Action.CREATED
        ));
    }
}
