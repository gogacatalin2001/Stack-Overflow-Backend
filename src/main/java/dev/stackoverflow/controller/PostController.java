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
    @GetMapping("/questions/get-all")
    @ResponseBody
    public List<Question> getAll() {
        return postService.getQuestions();
    }

    @GetMapping("/questions/get/{id}")
    @ResponseBody
    public Question getQuestion(@NonNull @PathVariable Long id) {
        return postService.getQuestion(id);
    }

    @PostMapping("/questions/save")
    @ResponseBody
    public Question saveQuestion(@NonNull @RequestBody QuestionTagWrapper wrapper) {
        return postService.saveQuestion(wrapper.getQuestion(), wrapper.getTags());
    }

    @PutMapping("/questions/update/{id}")
    @ResponseBody
    public Question updateQuestion(@NonNull @RequestBody Question question, @NonNull @PathVariable Long id) {
        return postService.updateQuestion(question, id);
    }

    @DeleteMapping("/questions/delete/{id}")
    public void deleteQuestion(@NonNull @PathVariable Long id) {
        postService.deleteQuestion(id);
    }

    //  ANSWERS
    @PostMapping("/answers/save/question-id={qid}")
    @ResponseBody
    public Answer saveAnswer(@NonNull @RequestBody Answer answer, @NonNull @PathVariable Long qid) {
        return postService.saveAnswer(answer, qid);
    }
    @GetMapping("/answers/get-all")
    @ResponseBody
    public List<Answer> getAnswers() {
        return postService.getAnswers();
    }

    @GetMapping("/answers/{id}")
    @ResponseBody
    public Answer getAnswerById(@NonNull @PathVariable Long id) {
        return postService.getAnswer(id);
    }

    @PutMapping("/answers/question-id={qid}/answer-id={aid}")
    @ResponseBody
    public Answer updateAnswer(@NonNull @RequestBody Answer answer, @NonNull @PathVariable Long aid, @NonNull @PathVariable Long qid) {
        return postService.updateAnswer(answer, aid, qid);
    }

    @DeleteMapping("/question-id={qid}/answer-id={aid}")
    public void deleteAnswer(@NonNull @PathVariable Long qid, @NonNull @PathVariable Long aid) {
        postService.deleteAnswer(qid, aid);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class QuestionTagWrapper {
        private Question question;
        private List<Tag> tags;
    }
}
