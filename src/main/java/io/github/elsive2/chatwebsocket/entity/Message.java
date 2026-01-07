package io.github.elsive2.chatwebsocket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(
    name = "message",
    indexes = {
        @Index(
            name = "message_ux1",
            columnList = "chat_id, message_chat_n desc, version desc",
            unique = true
        )
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user", "chat"})
public class Message {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "message_chat_n", nullable = false, updatable = false)
    private Integer messageChatN;

    @Setter
    @NotBlank
    @Column(name = "payload", nullable = false)
    private String payload;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Message() {
    }

    public Message(User user, Chat chat, String payload, int messageChatN) {
        this.user = Objects.requireNonNull(user);
        this.chat = Objects.requireNonNull(chat);
        this.payload = Objects.requireNonNull(payload);
        this.messageChatN = messageChatN;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
