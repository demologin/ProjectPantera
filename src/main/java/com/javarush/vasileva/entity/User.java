package com.javarush.vasileva.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = User.USER_GET_ALL,
                query = "select u from User u"
        )
})
public class User {

    public static final String USER_GET_ALL = "User.getAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login, String email, String password, Role role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @SuppressWarnings("unused")
    public boolean isAdmin() {
        return role != null && role.equals(Role.ADMIN);
    }
}
