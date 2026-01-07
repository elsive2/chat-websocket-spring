package io.github.elsive2.chatwebsocket.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chat")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Chat() {}

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
