package io.github.elsive2.chatwebsocket.event;

import io.github.elsive2.chatwebsocket.dto.MessageSentDto;

import java.util.UUID;

public record MessageEvent(
        UUID messageId,
        UUID chatId,
        String payload,
        MessageSentDto.Action action
) {
}
