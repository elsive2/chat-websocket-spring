package io.github.elsive2.chatwebsocket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends ServiceException {
    public MessageNotFoundException(final Object id) {
        super("Message not found with id " + id);
    }
}
