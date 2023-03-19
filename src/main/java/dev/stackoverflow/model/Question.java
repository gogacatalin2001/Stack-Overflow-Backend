package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question extends Post {
    @Column
    private String title;
    @ManyToMany
    private List<Tag> tags;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

//    public Question(String text, int voteCount, User author, @NonNull String title, @NonNull List<Tag> tags, List<Answer> answers) {
//        super(text, voteCount, author);
//        this.title = title;
//        this.tags = tags;
//        this.answers = answers;
//    }
//
//    public Question(Long id, String text, int voteCount, @NonNull User author, @NonNull String title, @NonNull List<Tag> tags, List<Answer> answers) {
//        super(id, text, voteCount, author);
//        this.title = title;
//        this.tags = tags;
//        this.answers = answers;
//    }

    public Answer addAnswer(@NonNull Answer answer) {
        this.answers.add(answer);
        return answer;
    }

    public Answer deleteAnswer(@NonNull Answer answer) {
        this.answers.remove(answer);
        return answer;
    }

    public List<Answer> getAnswers() {
        return answers.stream().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getTitle().equals(question.getTitle()) && getTags().equals(question.getTags()) && Objects.equals(getAnswers(), question.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getTags(), getAnswers());
    }
}
