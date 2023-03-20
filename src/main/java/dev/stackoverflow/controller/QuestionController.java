package dev.stackoverflow.controller;

import dev.stackoverflow.model.Question;
import dev.stackoverflow.service.QuestionService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    @ResponseBody
    public List<Question> getAll() {
        return questionService.getQuestions();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Question getQuestionById(@NonNull @PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    @PostMapping("/")
    public Question saveQuestion(@NonNull @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @PostMapping("/save-all")
    public List<Question> saveQuestions(@NonNull @RequestBody List<Question> questions) {
        return questionService.saveQuestions(questions);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@NonNull @RequestBody Question question, @NonNull @PathVariable Long id) {
        return questionService.updateQuestion(question, id);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@NonNull @PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}
