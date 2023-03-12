package dev.stackoverflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private Long id;
    private String title;
    private String text;
    // TODO add an image
    private User author;
    private LocalDateTime creationDateTime;
    private List<Tag> tags;
    private List<Answer> answerIds;
    private int voteNumber;
}
