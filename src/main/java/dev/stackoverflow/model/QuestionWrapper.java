package dev.stackoverflow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionWrapper {
    private Question question;
    private List<Tag> tags;
}
