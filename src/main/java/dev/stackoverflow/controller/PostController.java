package dev.stackoverflow.controller;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.QuestionTagWrapper;
import dev.stackoverflow.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "*", allowCredentials = "true")
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/questions/all")
    @ResponseBody
    public List<QuestionTagWrapper> getAll() {
        return postService.getQuestions();
    }

    @GetMapping("/questions")
    @ResponseBody
    public QuestionTagWrapper getQuestion(
            @NonNull @RequestParam("question_id") Long questionId
    ) {
        return postService.getQuestion(questionId);
    }

    @PostMapping("/questions")
    @ResponseBody
    public QuestionTagWrapper saveQuestion(
            @NonNull @RequestBody QuestionTagWrapper wrapper,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.saveQuestion(wrapper.getQuestion(), wrapper.getTags(), userId);
    }

    @PutMapping("/questions")
    @ResponseBody
    public QuestionTagWrapper updateQuestion(
            @NonNull @RequestBody Question question,
            @NonNull @RequestParam("question_id") Long questionId
    ) {
        return postService.updateQuestion(question, questionId);
    }

    @PutMapping("/questions/votes")
    @ResponseBody
    public QuestionTagWrapper updateQuestionVotes(
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("user_id") Long userId,
            @NonNull @RequestParam("vote") Integer vote
    ) {
        return postService.updateQuestionVotes(questionId, userId, vote);
    }

    @DeleteMapping("/questions")
    public void deleteQuestion(
            @NonNull @RequestParam("question_id") Long questionId
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
            @NonNull @RequestParam("answer_id") Long answerId
    ) {
        return postService.getAnswer(answerId);
    }

    @PostMapping("/answers")
    @ResponseBody
    public Answer saveAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.saveAnswer(answer, questionId, userId);
    }

    @PutMapping("/answers")
    @ResponseBody
    public Answer updateAnswer(
            @NonNull @RequestBody Answer answer,
            @NonNull @RequestParam("answer_id") Long answerId,
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.updateAnswer(answer, answerId, questionId, userId);
    }

    @PutMapping("/answers/votes")
    @ResponseBody
    public Answer updateAnswerVotes(
            @NonNull @RequestParam("answer_id") Long answerId,
            @NonNull @RequestParam("user_id") Long userId,
            @NonNull @RequestParam("vote") Integer vote

    ) {
        return postService.updateAnswerVotes(answerId, userId, vote);
    }

    @DeleteMapping("/answers")
    public void deleteAnswer(
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("answer_id") Long answerId
    ) {
        postService.deleteAnswer(questionId, answerId);
    }

    @GetMapping("/questions/answers")
    @ResponseBody
    public List<Answer> getAnswersByQuestionId(@NonNull @RequestParam("question_id") Long questionId) {
        return postService.getAnswersByQuestionId(questionId);
    }
}
