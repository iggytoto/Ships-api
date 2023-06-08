package org.vadimichi.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.vadimichi.service.Role;

import javax.persistence.*;
import java.io.Serializable;

import static org.vadimichi.model.auth.User.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
public class User implements Serializable {

    public static final String TABLE_NAME = "users";
    public static final String LOGIN_COLUMN_NAME = "name";
    public static final String PASSWORD_SHA_COLUMN_NAME = "password_sha";
    public static final String ROLE_COLUMN_NAME = "role";
    public static final String SEQUENCE_NAME = "s_users_id";

    protected User() {
    }

    protected User(String login, String passwordSha, Role role) {
        this.login = login;
        this.passwordSha = passwordSha;
        this.role = role;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, allocationSize = 1)
    protected Long id;

    @Column(name = LOGIN_COLUMN_NAME)
    private String login;

    @Column(name = PASSWORD_SHA_COLUMN_NAME)
    private String passwordSha;

    @Column(name = ROLE_COLUMN_NAME)
    @Enumerated(EnumType.STRING)
    private Role role;

    public static User PlayerOf(String login, String passwordSha) {
        return new User(login, passwordSha, Role.Player);
    }
}
