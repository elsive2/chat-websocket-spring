package io.github.elsive2.chatwebsocket.repostiory;

import io.github.elsive2.chatwebsocket.entity.Chat;
import io.github.elsive2.chatwebsocket.exception.ChatNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Logger log = LoggerFactory.getLogger(ChatRepository.class);

    default Chat findByIdRequired(final UUID id) {
        log.debug("Finding chat by id {}", id);
        return findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    @Query(
            value = """
                update chat
                set message_chat_n = message_chat_n + 1
                where id = :chatId
                returning message_chat_n
            """,
            nativeQuery = true
    )
    Integer nextMessageChatN(@Param("chatId") UUID chatId);
}
