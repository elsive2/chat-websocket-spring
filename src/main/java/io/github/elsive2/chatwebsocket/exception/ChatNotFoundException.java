package io.github.elsive2.chatwebsocket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ChatNotFoundException extends ServiceException {
    public ChatNotFoundException(final Object id) {
        super("Chat not found with id " + id);
    }
}
