package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table
public class Question extends Post implements Comparable<Question> {
    @Column(unique = true)
    private String title;
    @OrderBy(value = "voteCount desc ")
    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Answer> answers;

    public Question() {
    }

    public void addAnswer(@NonNull Answer answer) {
        this.answers.add(answer);
    }

    public void deleteAnswer(@NonNull Answer answer) {
        this.answers.remove(answer);
    }

    @Override
    public int compareTo(Question q) {

        return this.creationDateTime.compareTo(q.creationDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return this.title.equals(question.getTitle()) && this.answers.equals(question.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle(), getAnswers());
    }

    @Override
    public String toString() {
        return "Question{" +
                "answers=" + answers +
                ", text='" + text + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", voteCount=" + voteCount +
                ", user=" + user +
                '}';
    }
}
