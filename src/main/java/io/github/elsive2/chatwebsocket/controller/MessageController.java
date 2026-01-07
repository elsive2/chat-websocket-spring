package io.github.elsive2.chatwebsocket.controller;

import io.github.elsive2.chatwebsocket.dto.request.MessageUpdateDto;
import io.github.elsive2.chatwebsocket.dto.response.MessageDto;
import io.github.elsive2.chatwebsocket.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PutMapping("/{id}")
    public MessageDto update(
            @PathVariable final UUID id,
            @RequestBody @Valid final MessageUpdateDto messageUpdateDto
    ) {
        return messageService.update(id, messageUpdateDto);
    }


    /**
     * Cursor-based pagination
     */
    @GetMapping
    public List<MessageDto> getMessages(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID chatId,
            @RequestParam(required = false) Integer after,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return messageService.getMessages(
                userId,
                chatId,
                after,
                limit
        );
    }
}
