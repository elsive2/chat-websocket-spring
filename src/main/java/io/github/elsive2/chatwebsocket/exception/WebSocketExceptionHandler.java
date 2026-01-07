package io.github.elsive2.chatwebsocket.exception;

import io.github.elsive2.chatwebsocket.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler({
            ChatNotFoundException.class,
            MessageNotFoundException.class,
            UserNotFoundException.class
    })
    @SendToUser("/queue/errors")
    public ErrorResponse handleNotFound(ServiceException ex, SimpMessageHeaderAccessor headers) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), headers, null);
    }

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException ex,
            SimpMessageHeaderAccessor headers
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", headers, errors);
    }

    @MessageExceptionHandler(ConstraintViolationException.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleConstraintViolation(
            ConstraintViolationException ex,
            SimpMessageHeaderAccessor headers
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations()
                .forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", headers, errors);
    }

    @MessageExceptionHandler(OptimisticLockingFailureException.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleOptimisticLock(
            OptimisticLockingFailureException ex,
            SimpMessageHeaderAccessor headers
    ) {
        String message = ex.getMessage() == null ? "Conflict detected" : ex.getMessage();
        return buildResponse(HttpStatus.CONFLICT, message, headers, null);
    }

    @MessageExceptionHandler(DataIntegrityViolationException.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleDataIntegrity(
            DataIntegrityViolationException ex,
            SimpMessageHeaderAccessor headers
    ) {
        return buildResponse(HttpStatus.CONFLICT, "Data integrity violation", headers, null);
    }

    @MessageExceptionHandler(ServiceException.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleServiceException(
            ServiceException ex,
            SimpMessageHeaderAccessor headers
    ) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), headers, null);
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public ErrorResponse handleUnexpected(Exception ex, SimpMessageHeaderAccessor headers) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", headers, null);
    }

    private ErrorResponse buildResponse(
            HttpStatus status,
            String message,
            SimpMessageHeaderAccessor headers,
            Map<String, String> validationErrors
    ) {
        String resolvedMessage = message == null || message.isBlank()
                ? status.getReasonPhrase()
                : message;
        String path = headers == null || headers.getDestination() == null
                ? "/ws"
                : headers.getDestination();
        return new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                resolvedMessage,
                path,
                validationErrors
        );
    }
}
