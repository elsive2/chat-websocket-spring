package io.github.elsive2.chatwebsocket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 4000)
    private String message;
}
