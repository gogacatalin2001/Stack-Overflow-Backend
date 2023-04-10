package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table
public class Question extends Post implements Comparable<Question> {
    @Column(unique = true)
    private String title;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Answer> answers = new ArrayList<>();

    public Question() {

    }

    public void addAnswer(@NonNull Answer answer) {
        this.answers.add(answer);
    }

    public void deleteAnswer(@NonNull Answer answer) {
        this.answers.remove(answer);
    }

    public List<Answer> getAnswers() {
        return answers.stream().toList();
    }

    @Override
    public int compareTo(Question q) {
        return this.creationDateTime.compareTo(q.creationDateTime);
    }
}
