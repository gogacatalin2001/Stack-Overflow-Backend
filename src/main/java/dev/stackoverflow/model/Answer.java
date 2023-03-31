package dev.stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table()
public class Answer extends Post implements Comparable<Answer> {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    public Answer() {
    }

    public Answer(String text, byte[] imageData, int voteCount, User user) {
        super(text, imageData, voteCount, user);
    }

    public Answer(Long id, byte[] imageData, String text, int voteCount, User user) {
        super(id, imageData, text, voteCount, user);
    }

    @Override
    public int compareTo(Answer a) {
        return this.voteCount - a.voteCount;
    }
}
