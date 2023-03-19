package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    @Column(name = "first_name", columnDefinition = "varchar(50) default 'FirstName'", nullable = false)
    private String firstName;
    @Column(name = "last_name", columnDefinition = "varchar(50) default 'LastName'", nullable = false)
    private String lastName;
    @Column(name = "email", columnDefinition = "varchar(50) default 'defaultemail@provider.com'", nullable = false, unique = true)
    private String email;
    @Column(name = "password", columnDefinition = "varchar(50) default 'password'", nullable = false)
    private String password;
    @Column(name = "phone_number", columnDefinition = "varchar(50) default '0000000000'", nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "score", columnDefinition = "int default 0", nullable = false)
    private int score;
    @Column(name = "banned", columnDefinition = "bit(1) default false", nullable = false)
    private boolean banned;
    @Column(name = "moderator", columnDefinition = "bit(1) default false", nullable = false)
    private boolean moderator;
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Answer> answers = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, int score, boolean banned, boolean moderator, List<Question> questions, List<Answer> answers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.score = score;
        this.banned = banned;
        this.moderator = moderator;
        this.questions = questions;
        this.answers = answers;
    }

    public User(Long userId, String firstName, String lastName, String email, String password, String phoneNumber, int score, boolean banned, boolean moderator, List<Question> questions, List<Answer> answers) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.score = score;
        this.banned = banned;
        this.moderator = moderator;
        this.questions = questions;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
