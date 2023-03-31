package dev.stackoverflow.controller;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.Tag;
import dev.stackoverflow.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    //  QUESTIONS
    @GetMapping("/questions")
    @ResponseBody
    public List<Question> getAll() {
        return postService.getQuestions();
    }

    @GetMapping("/questions/{question-id}")
    @ResponseBody
    public Question getQuestion(
            @NonNull @PathVariable("question-id") Long questionId
    ) {
        return postService.getQuestion(questionId);
    }

    @PostMapping("/questions")
    @ResponseBody
    public Question saveQuestion(
            @NonNull @RequestBody QuestionTagWrapper wrapper
    ) {
        return postService.saveQuestion(wrapper.getQuestion(), wrapper.getTags());
    }

    @PutMapping("/questions/{question-id}")
    @ResponseBody
    public Question updateQuestion(
            @NonNull @RequestBody Question question,
            @NonNull @PathVariable("question-id") Long questionId
    ) {
        return postService.updateQuestion(question, questionId);
    }

    @DeleteMapping("/questions/{question-id}")
    public void deleteQuestion(
            @NonNull @PathVariable("question-id") Long questionId
    ) {
        postService.deleteQuestion(questionId);
    }

    //  ANSWERS
    @PostMapping("/answers/{question-id}")
    @ResponseBody
    public Answer saveAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @PathVariable("question-id") Long questionId
    ) {
        return postService.saveAnswer(answer, questionId);
    }
    @GetMapping("/answers")
    @ResponseBody
    public List<Answer> getAnswers() {
        return postService.getAnswers();
    }

    @GetMapping("/answers/{answer-id}")
    @ResponseBody
    public Answer getAnswerById(
            @NonNull @PathVariable("answer-id") Long answerId
    ) {
        return postService.getAnswer(answerId);
    }

    @PutMapping("/answers/{answer-id}/{question-id}")
    @ResponseBody
    public Answer updateAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @PathVariable("answer-id") Long answerId,
            @NonNull @PathVariable("question-id") Long questionId
    ) {
        return postService.updateAnswer(answer, answerId, questionId);
    }

    @DeleteMapping("/answers/{answer-id}/{question-id}")
    public void deleteAnswer(
            @NonNull @PathVariable("question-id") Long questionId,
            @NonNull @PathVariable("answer-id") Long answerId
    ) {
        postService.deleteAnswer(questionId, answerId);
    }

    @GetMapping("/answers/{question-id}")
    @ResponseBody
    public List<Answer> getAnswersByQuestionId(@NonNull @PathVariable("question-id") Long questionId) {
        return postService.getAnswersByQuestionId(questionId);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class QuestionTagWrapper {
        private Question question;
        private List<Tag> tags;
    }
}
