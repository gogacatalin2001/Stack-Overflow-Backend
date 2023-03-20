package dev.stackoverflow.controller;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.service.AnswerService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/")
    @ResponseBody
    public List<Answer> getAll() {
        return answerService.getAnswers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Answer getAnswerById(@NonNull @PathVariable Long id) {
        return answerService.getAnswer(id);
    }

    @PostMapping("/")
    public Answer saveAnswer(@NonNull @RequestBody Answer answer) {
        return answerService.saveAnswer(answer);
    }

    @PostMapping("/save-all")
    public List<Answer> saveAnswers(@NonNull @RequestBody List<Answer> answers) {
        return answerService.saveAnswers(answers);
    }

    @PutMapping("/{id}")
    public Answer updateAnswer(@NonNull @RequestBody Answer answer, @NonNull @PathVariable Long id) {
        return answerService.updateAnswer(answer, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@NonNull @PathVariable Long id) {
        answerService.deleteAnswer(id);
    }
}
