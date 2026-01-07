package io.github.elsive2.chatwebsocket.handler.ws;

import io.github.elsive2.chatwebsocket.dto.request.MessageQueryDto;
import io.github.elsive2.chatwebsocket.dto.request.MessageUpdateDto;
import io.github.elsive2.chatwebsocket.dto.request.MessageUpdateWsDto;
import io.github.elsive2.chatwebsocket.dto.response.MessageDto;
import io.github.elsive2.chatwebsocket.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Validated
public class MessageWsController {
    private static final int DEFAULT_LIMIT = 50;

    private final MessageService messageService;

    @MessageMapping("/messages/update")
    public void update(@Payload @Valid final MessageUpdateWsDto payload) {
        MessageUpdateDto updateDto = new MessageUpdateDto();
        updateDto.setMessage(payload.getMessage());
        updateDto.setVersion(payload.getVersion());
        messageService.update(payload.getMessageId(), updateDto);
    }

    @MessageMapping("/messages/query")
    @SendToUser("/queue/messages-page")
    public List<MessageDto> query(@Payload @Valid final MessageQueryDto payload) {
        int limit = payload.getLimit() == null ? DEFAULT_LIMIT : payload.getLimit();
        return messageService.getMessages(
                payload.getUserId(),
                payload.getChatId(),
                payload.getAfter(),
                limit
        );
    }
}
