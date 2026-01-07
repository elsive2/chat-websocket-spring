package io.github.elsive2.chatwebsocket.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "app_user")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    protected User() {}

    public User(String username) {
        this.username = Objects.requireNonNull(username);
    }
}
