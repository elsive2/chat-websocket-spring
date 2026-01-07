package io.github.elsive2.chatwebsocket.service;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;
import io.github.elsive2.chatwebsocket.dto.request.MessageUpdateDto;
import io.github.elsive2.chatwebsocket.dto.response.MessageDto;
import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.event.MessageEvent;
import io.github.elsive2.chatwebsocket.exception.ServiceException;
import io.github.elsive2.chatwebsocket.repostiory.MessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public MessageDto update(UUID id, @Valid MessageUpdateDto messageUpdateDto) {
        Message message = messageRepository.findByIdRequired(id);

        if (!Objects.equals(message.getVersion(), messageUpdateDto.getVersion())) {
            throw new OptimisticLockingFailureException("Message was modified by another user");
        }

        message.setPayload(messageUpdateDto.getMessage());
        messageRepository.saveAndFlush(message);

        eventPublisher.publishEvent(new MessageEvent(
                message.getId(),
                message.getChat().getId(),
                message.getPayload(),
                message.getMessageChatN(),
                message.getVersion(),
                MessageSentDto.Action.UPDATED
        ));

        return MessageDto.fromEntity(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> getMessages(
            UUID userId,
            UUID chatId,
            Integer after,
            int limit
    ) {
        if (after != null && chatId == null) {
            throw new ServiceException("Cursor pagination requires chatId");
        }
        Pageable pageable = PageRequest.of(0, limit);

        List<Message> messages = messageRepository.findNextPage(
                userId,
                chatId,
                after,
                pageable
        );

        return messages.stream()
                .map(MessageDto::fromEntity)
                .toList();
    }
}
