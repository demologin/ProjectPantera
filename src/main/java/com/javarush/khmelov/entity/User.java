package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString(exclude = {"quests", "games"})
@NamedQueries({
        @NamedQuery(
                name = User.GET_ALL,
                query = "select u from User u"
        ),
        @NamedQuery(
                name = User.BETWEEN_START_AND_END,
                query = "select u from User u where u.id between :startId and :endId"
        )
})
public class User implements AbstractEntity {

    public static final String BETWEEN_START_AND_END = "User.between startId And endId";

    public static final String GET_ALL = "User.getAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private final Collection<Quest> quests = new ArrayList<>();

    @Transient
    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        return "user-" + id;
    }

    @Override
    public boolean equals(Object o) {
        Class<?> entityClass = Hibernate.getClass(o);
        if (o == null || this.getClass() != entityClass) return false;

        User user = (User) o;
        return getId()!=null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
