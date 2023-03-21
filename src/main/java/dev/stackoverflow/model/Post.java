package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    //private User user;
    @Column
    protected String text;

    @Column
    private byte[] imageData;

    @Column
    protected LocalDateTime creationDateTime = LocalDateTime.now();
    @Column
    protected int voteCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    public Post() {
    }

    public Post(String text, byte[] imageData, int voteCount, User user) {
        this.text = text;
        this.imageData = imageData;
        this.creationDateTime = LocalDateTime.now();
        this.voteCount = voteCount;
        this.user = user;
    }

    public Post(Long id, byte[] imageData, String text, int voteCount, User user) {
        this.id = id;
        this.text = text;
        this.imageData = imageData;
        this.creationDateTime = LocalDateTime.now();
        this.voteCount = voteCount;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return voteCount == post.voteCount && id.equals(post.id) && text.equals(post.text) && creationDateTime.equals(post.creationDateTime) && user.equals(post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationDateTime, voteCount, user);
    }
}


