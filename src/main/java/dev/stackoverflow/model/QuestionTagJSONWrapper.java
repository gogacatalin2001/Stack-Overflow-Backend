package dev.stackoverflow.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionTagJSONWrapper {

    private Question question;
    private List<Tag> tags;
}
