package io.github.elsive2.chatwebsocket.repostiory;

import io.github.elsive2.chatwebsocket.entity.Chat;
import io.github.elsive2.chatwebsocket.exception.ChatNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Logger log = LoggerFactory.getLogger(ChatRepository.class);

    default Chat findByIdRequired(final UUID id) {
        log.debug("Finding chat by id {}", id);
        return findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }
}
