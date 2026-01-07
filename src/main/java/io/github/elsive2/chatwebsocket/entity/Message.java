package io.github.elsive2.chatwebsocket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

// TODO: CREATE UNIQUE INDEX message_ux1 ON message (chat_id ASC, message_chat_n DESC, version DESC
@Entity
@Table(name = "message")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user", "chat"})
public class Message extends BaseEntity {
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Getter
    @Column(name = "message_chat_n", nullable = false, updatable = false)
    private Integer messageChatN;

    @Getter
    @NotBlank
    @Column(name = "payload", nullable = false)
    private String payload;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    protected Message() {
    }

    public Message(User user, Chat chat, String payload, int messageChatN) {
        this.user = Objects.requireNonNull(user);
        this.chat = Objects.requireNonNull(chat);
        this.payload = Objects.requireNonNull(payload);
        this.messageChatN = messageChatN;
    }
}
