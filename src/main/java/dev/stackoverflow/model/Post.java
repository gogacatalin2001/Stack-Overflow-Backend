package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    protected String text;
    @Column
    private String imageURL;
    @Column
    protected LocalDateTime creationDateTime = LocalDateTime.now();
    @Column
    protected Integer voteCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    public Post() {
    }

//    public Post(@NonNull String text, String imageData, @NonNull User user) {
//        this.text = text;
//        this.imageURL = imageData;
//        this.creationDateTime = LocalDateTime.now();
//        this.voteCount = 0;
//        this.user = user;
//    }
//
//    public Post(@NonNull Long id, String imageData, @NonNull String text, @NonNull Integer voteCount, @NonNull User user) {
//        this.id = id;
//        this.text = text;
//        this.imageURL = imageData;
//        this.creationDateTime = LocalDateTime.now();
//        this.voteCount = voteCount;
//        this.user = user;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return voteCount.equals(post.voteCount) && id.equals(post.id) && text.equals(post.text) && creationDateTime.equals(post.creationDateTime) && user.equals(post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationDateTime, voteCount, user);
    }
}


