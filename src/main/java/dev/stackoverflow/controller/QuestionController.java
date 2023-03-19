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
    public void saveQuestion(@NonNull @RequestBody Question question) {
        questionService.saveQuestion(question);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@NonNull @RequestBody Question question, @NonNull @PathVariable Long id) {
        return questionService.updateQuestion(question, id);
    }
}
