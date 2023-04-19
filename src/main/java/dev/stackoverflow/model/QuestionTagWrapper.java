package dev.stackoverflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionTagWrapper {
    private Question question;
    private List<Tag> tags;
}
