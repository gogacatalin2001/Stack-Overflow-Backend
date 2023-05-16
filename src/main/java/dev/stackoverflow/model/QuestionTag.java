package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class QuestionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long questionTagId;
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    public QuestionTag(@NonNull Question question, @NonNull Tag tag) {
        this.question = question;
        this.tag = tag;
    }

    // TODO equals() DOES NOT WORK
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionTag that = (QuestionTag) o;
        return this.question.equals(that.getQuestion()) && this.tag.equals(that.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestion(), getTag());
    }

    @Override
    public String toString() {
        return "QuestionTag{" +
                "questionTagId=" + questionTagId +
                ", question=" + question +
                ", tag=" + tag +
                '}';
    }
}
