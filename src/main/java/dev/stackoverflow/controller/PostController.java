package dev.stackoverflow.controller;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.Tag;
import dev.stackoverflow.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/questions/all")
    @ResponseBody
    public List<Question> getAll() {
        return postService.getQuestions();
    }

    @GetMapping("/questions")
    @ResponseBody
    public Question getQuestion(
            @NonNull @RequestParam("question-id") Long questionId
    ) {
        return postService.getQuestion(questionId);
    }

    @PostMapping("/questions")
    @ResponseBody
    public Question saveQuestion(
            @NonNull @RequestBody QuestionTagWrapper wrapper,
            @NonNull @RequestParam("user-id") Long userId
    ) {
        return postService.saveQuestion(wrapper.getQuestion(), wrapper.getTags(), userId);
    }
    //    @PreAuthorize("")
    @PutMapping("/questions")
    @ResponseBody
    public Question updateQuestion(
            @NonNull @RequestBody Question question,
            @NonNull @RequestParam("question-id") Long questionId
    ) {
        return postService.updateQuestion(question, questionId);
    }

    @PutMapping("/questions/votes")
    @ResponseBody
    public Question updateQuestionVotes(
            @NonNull @RequestParam("question-id") Long questionId,
            @NonNull @RequestParam("user-id") Long userId,
            @NonNull @RequestParam("vote") Integer vote
    ) {
        return postService.updateQuestionVotes(questionId, userId, vote);
    }

    @DeleteMapping("/questions")
    public void deleteQuestion(
            @NonNull @RequestParam("question-id") Long questionId
    ) {
        postService.deleteQuestion(questionId);
    }

    @GetMapping("/answers/all")
    @ResponseBody
    public List<Answer> getAnswers() {
        return postService.getAnswers();
    }

    @GetMapping("/answers")
    @ResponseBody
    public Answer getAnswerById(
            @NonNull @RequestParam("answer-id") Long answerId
    ) {
        return postService.getAnswer(answerId);
    }

    @PostMapping("/answers")
    @ResponseBody
    public Answer saveAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @RequestParam("question-id") Long questionId,
            @NonNull @RequestParam("user-id") Long userId
    ) {
        return postService.saveAnswer(answer, questionId, userId);
    }

    @PutMapping("/answers")
    @ResponseBody
    public Answer updateAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @RequestParam("answer-id") Long answerId,
            @NonNull @RequestParam("question-id") Long questionId,
            @NonNull @RequestParam("user-id") Long userId
    ) {
        return postService.updateAnswer(answer, answerId, questionId, userId);
    }

    @PutMapping("/answers/votes")
    @ResponseBody
    public Answer updateAnswerVotes(
            @NonNull @RequestParam("answer-id") Long answerId,
            @NonNull @RequestParam("user-id") Long userId,
            @NonNull @RequestParam("vote") Integer vote

    ) {
        return postService.updateAnswerVotes(answerId, userId, vote);
    }

    @DeleteMapping("/answers")
    public void deleteAnswer(
            @NonNull @RequestParam("question-id") Long questionId,
            @NonNull @RequestParam("answer-id") Long answerId
    ) {
        postService.deleteAnswer(questionId, answerId);
    }

    @GetMapping("/questions/answers")
    @ResponseBody
    public List<Answer> getAnswersByQuestionId(@NonNull @RequestParam("question-id") Long questionId) {
        return postService.getAnswersByQuestionId(questionId);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class QuestionTagWrapper {
        private Question question;
        private List<Tag> tags;
    }
}
