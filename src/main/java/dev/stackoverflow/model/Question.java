package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question extends Post {

    @NonNull
    @Column(name = "title", columnDefinition = "varchar(200) default 'Question Title'", nullable = false)
    private String title;
    @NonNull
    @ManyToMany
    private List<Tag> tags;
    @OneToMany
    private List<Answer> answers;


}
