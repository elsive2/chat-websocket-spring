package io.github.elsive2.chatwebsocket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public final class ChatMessageDto {
    @NotNull
    private UUID chatId;

    @NotNull
    private UUID userId;

    @NotBlank
    // TODO: Validation
    private String message;
}
