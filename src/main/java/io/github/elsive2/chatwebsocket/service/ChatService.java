package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.ChatMessageDto;
import io.github.elsive2.chatwebsocket.entity.Chat;
import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.entity.User;
import io.github.elsive2.chatwebsocket.exception.UserNotFoundException;
import io.github.elsive2.chatwebsocket.repostiory.ChatRepository;
import io.github.elsive2.chatwebsocket.repostiory.MessageRepository;
import io.github.elsive2.chatwebsocket.repostiory.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void processMessage(final @Valid ChatMessageDto payload) {
        log.debug("Got message - {}", payload);

        Chat chat = chatRepository.findByIdRequired(payload.getChatId());
        User user = userRepository.findByIdRequired(payload.getUserId());

        Message message = new Message(user, chat, payload.getMessage(), chat.incrAndGetMessageChatN());

        log.debug("Saving message - {}", message);
        messageRepository.save(message);

        log.debug("Publish message to subs");
        messagingTemplate.convertAndSendToUser(
                payload.getChatId().toString(),
                "/queue/messages",
                payload

        );
    }
}
