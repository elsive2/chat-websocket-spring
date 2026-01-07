package io.github.elsive2.chatwebsocket.repostiory;

import io.github.elsive2.chatwebsocket.entity.User;
import io.github.elsive2.chatwebsocket.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Logger log = LoggerFactory.getLogger(UserRepository.class);

    default User findByIdRequired(final UUID id) {
        log.debug("Finding user by id {}", id);
        return findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
