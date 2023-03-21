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

//    @GetMapping("/get-all")
//    @ResponseBody
//    public List<Question> getAll() {
//        return questionService.getQuestions();
//    }
//
//    @GetMapping("/get/{id}")
//    @ResponseBody
//    public Question getQuestionById(@NonNull @PathVariable Long id) {
//        return questionService.getQuestion(id);
//    }
//
//    @PostMapping("/save")
//    public Question saveQuestion(@NonNull @RequestBody Question question) {
//        return questionService.saveQuestion(question);
//    }
//
//    @PutMapping("/update/{id}")
//    public Question updateQuestion(@NonNull @RequestBody Question question, @NonNull @PathVariable Long id) {
//        return questionService.updateQuestion(question, id);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public void deleteQuestion(@NonNull @PathVariable Long id) {
//        questionService.deleteQuestion(id);
//    }
}
