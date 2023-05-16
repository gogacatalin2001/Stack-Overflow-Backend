package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    protected String text;
    @Column
    protected LocalDateTime creationDateTime = LocalDateTime.now();
    @Column
    protected Integer voteCount;
    @JoinColumn(name = "image_id")
    @ManyToOne
    private Image image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
}


