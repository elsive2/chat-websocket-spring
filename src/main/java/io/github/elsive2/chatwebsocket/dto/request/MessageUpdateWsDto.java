package io.github.elsive2.chatwebsocket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageUpdateWsDto {
    @NotNull
    private UUID messageId;

    @NotBlank
    @Size(min = 1, max = 255)
    private String message;

    @NotNull
    @PositiveOrZero
    private Integer version;
}
