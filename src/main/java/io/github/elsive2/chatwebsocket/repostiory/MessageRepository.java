package io.github.elsive2.chatwebsocket.repostiory;

import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.exception.MessageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Logger log = LoggerFactory.getLogger(MessageRepository.class);

    default Message findByIdRequired(UUID id) {
        log.debug("Finding message by id {}", id);
        return findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }
}
