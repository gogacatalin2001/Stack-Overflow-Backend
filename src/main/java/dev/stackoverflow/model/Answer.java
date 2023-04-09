package dev.stackoverflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table
public class Answer extends Post implements Comparable<Answer> {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() {
    }

//    public Answer(String text, byte[] imageData, @NonNull User user) {
//        super(text, imageData, user);
//    }
//
//    public Answer(Long id, byte[] imageData, String text, Integer voteCount, User user) {
//        super(id, imageData, text, voteCount, user);
//    }

    @Override
    public int compareTo(Answer a) {
        return this.voteCount - a.voteCount;
    }
}
