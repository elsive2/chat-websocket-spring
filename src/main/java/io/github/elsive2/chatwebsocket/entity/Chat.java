package io.github.elsive2.chatwebsocket.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "chat")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "message_chat_n", nullable = false)
    private Integer messageChatN = 0;

    protected Chat() {}

    public int incrAndGetMessageChatN() {
        return ++this.messageChatN;
    }
}
