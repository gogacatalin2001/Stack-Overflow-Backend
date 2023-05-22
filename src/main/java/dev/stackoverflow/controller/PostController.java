package dev.stackoverflow.controller;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.QuestionWrapper;
import dev.stackoverflow.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "*", allowCredentials = "true")
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/questions/all")
    @ResponseBody
    public List<QuestionWrapper> getAll() {
        return postService.getQuestions();
    }

    @GetMapping("/questions/get")
    @ResponseBody
    public QuestionWrapper getQuestion(
            @NonNull @RequestParam("question_id") Long questionId
    ) {
        return postService.getQuestion(questionId);
    }

    @PostMapping("/questions")
    @ResponseBody
    public QuestionWrapper saveQuestion(
            @NonNull @RequestBody QuestionWrapper wrapper,
            @RequestParam("image_id") Long imageId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.saveQuestion(wrapper.getQuestion(), wrapper.getTags(), userId, imageId);
    }

    @PutMapping("/questions")
    @ResponseBody
    public QuestionWrapper updateQuestion(
            @NonNull @RequestBody QuestionWrapper questionWrapper,
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("user_id") Long userId,
            @RequestParam("image_id") Long imageId
    ) throws IOException {
        return postService.updateQuestion(questionWrapper, imageId, questionId, userId);
    }

    @PatchMapping("/questions/votes")
    @ResponseBody
    public QuestionWrapper updateQuestionVotes(
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
            @RequestParam("image_id") Long imageId,
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.saveAnswer(answer, imageId, questionId, userId);
    }

    @PutMapping("/answers")
    @ResponseBody
    public Answer updateAnswer(
            // todo maybe a wrapper will be needed
            @NonNull @RequestBody Answer answer,
            @RequestParam("image_id") Long imageId,
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("answer_id") Long answerId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        return postService.updateAnswer(answer, imageId, questionId, answerId, userId);
    }

    @PatchMapping("/answers/votes")
    @ResponseBody
    public Answer updateAnswerVotes(
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("answer_id") Long answerId,
            @NonNull @RequestParam("user_id") Long userId,
            @NonNull @RequestParam("vote") Integer vote

    ) {
        return postService.updateAnswerVotes(questionId, answerId, userId, vote);
    }

    @DeleteMapping("/answers")
    public void deleteAnswer(
            @NonNull @RequestParam("question_id") Long questionId,
            @NonNull @RequestParam("answer_id") Long answerId,
            @NonNull @RequestParam("user_id") Long userId
    ) {
        postService.deleteAnswer(questionId, answerId, userId);
    }
}
