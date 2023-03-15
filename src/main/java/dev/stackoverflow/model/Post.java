package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    //private User author;
    @NonNull
    @Column(name = "text", columnDefinition = "varchar(500) default 'no text'", nullable = false)
    private String text;

    // TODO add picture

    @NonNull
    @Column(name = "creation_date_time", nullable = false)
    private LocalDateTime creationDateTime;
    @NonNull
    @Column(name = "vote_count", columnDefinition = "int default 0", nullable = false)
    private int voteCount;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
