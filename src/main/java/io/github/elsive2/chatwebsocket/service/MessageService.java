package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;
import io.github.elsive2.chatwebsocket.dto.request.MessageUpdateDto;
import io.github.elsive2.chatwebsocket.dto.response.MessageDto;
import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.repostiory.MessageRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final WebSocketService webSocketService;

    @Transactional
    public MessageDto update(UUID id, @Valid MessageUpdateDto messageUpdateDto) {
        Message message = messageRepository.findByIdRequired(id);

        if (!Objects.equals(message.getVersion(), messageUpdateDto.getVersion())) {
            throw new OptimisticLockingFailureException("Message was modified by another user");
        }

        message.setPayload(messageUpdateDto.getMessage());

        webSocketService.sendChatMessage(message, MessageSentDto.Action.UPDATED);

        return new MessageDto(message.getId(), message.getPayload(), message.getVersion());
    }
}
