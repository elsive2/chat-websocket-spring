package io.github.elsive2.chatwebsocket.repostiory;

import io.github.elsive2.chatwebsocket.entity.Message;
import io.github.elsive2.chatwebsocket.exception.MessageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Logger log = LoggerFactory.getLogger(MessageRepository.class);

    @Query("""
        select m from Message m
        where (:chatId is null or m.chat.id = :chatId)
          and (:userId is null or m.user.id = :userId)
          and (:after is null or m.messageChatN > :after)
        order by m.messageChatN asc
    """)
    List<Message> findNextPage(
            UUID userId,
            UUID chatId,
            Integer after,
            Pageable pageable
    );

    default Message findByIdRequired(UUID id) {
        log.debug("Finding message by id {}", id);
        return findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }
}
