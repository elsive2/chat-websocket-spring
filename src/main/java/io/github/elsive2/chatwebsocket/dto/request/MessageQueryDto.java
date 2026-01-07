package io.github.elsive2.chatwebsocket.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageQueryDto {
    private UUID userId;
    private UUID chatId;

    @PositiveOrZero
    private Integer after;

    @Min(1)
    @Max(200)
    private Integer limit;
}
