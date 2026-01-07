package io.github.elsive2.chatwebsocket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(final Object id) {
        super("User not found with id " + id);
    }
}
