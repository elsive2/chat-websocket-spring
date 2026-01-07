package io.github.elsive2.chatwebsocket.dto.request;

import lombok.Data;

@Data
public class MessageUpdateDto {
    private String message; // TODO: Validation
    private Integer version;

}
