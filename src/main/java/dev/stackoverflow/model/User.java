package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    @NonNull
    @Column(name = "first_name", columnDefinition = "varchar(50) default 'FirstName'", nullable = false)
    private String firstName;
    @NonNull
    @Column(name = "last_name", columnDefinition = "varchar(50) default 'LastName'", nullable = false)
    private String lastName;
    @NonNull
    @Column(name = "email", columnDefinition = "varchar(50) default 'defaultemail@provider.com'", nullable = false, unique = true)
    private String email;
    @NonNull
    @Column(name = "password", columnDefinition = "varchar(50) default 'password'", nullable = false)
    private String password;
    @NonNull
    @Column(name = "phone_number", columnDefinition = "varchar(50) default '0000000000'", nullable = false, unique = true)
    private String phoneNumber;
    @NonNull
    @Column(name = "score", columnDefinition = "int default 0", nullable = false)
    private int score;
    @NonNull
    @Column(name = "banned", columnDefinition = "bit(1) default false", nullable = false)
    private boolean banned;
    @NonNull
    @Column(name = "moderator", columnDefinition = "bit(1) default false", nullable = false)
    private boolean moderator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
