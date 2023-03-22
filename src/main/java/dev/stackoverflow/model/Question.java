package dev.stackoverflow.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question extends Post {
    @Column(unique = true)
    private String title;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(String text, byte[] imageData, int voteCount, User user, String title, List<Answer> answers) {
        super(text, imageData, voteCount, user);
        this.title = title;
        this.answers = answers;
    }

    public Question(Long id, byte[] imageData, String text, int voteCount, User user, String title, List<Answer> answers) {
        super(id, imageData, text, voteCount, user);
        this.title = title;
        this.answers = answers;
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
}
