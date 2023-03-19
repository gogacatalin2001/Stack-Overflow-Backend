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
    protected Long id;
    //private User author;
    @Column
    protected String text;

    // TODO add picture

    @Column
    protected LocalDateTime creationDateTime;
    @Column
    protected int voteCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User author;

    public Post() {
    }

    public Post(String text, int voteCount, User author) {
        this.text = text;
        this.creationDateTime = LocalDateTime.now();
        this.voteCount = voteCount;
        this.author = author;
    }

    public Post(Long id, String text, int voteCount, User author) {
        this.id = id;
        this.text = text;
        this.creationDateTime = LocalDateTime.now();
        this.voteCount = voteCount;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return voteCount == post.voteCount && id.equals(post.id) && text.equals(post.text) && creationDateTime.equals(post.creationDateTime) && author.equals(post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationDateTime, voteCount, author);
    }
}


