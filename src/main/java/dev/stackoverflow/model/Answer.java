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
public class Answer extends Post implements Comparable<Answer>{

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() {
    }

    @Override
    public int compareTo(Answer a) {
        return this.voteCount - a.voteCount;
    }
}
