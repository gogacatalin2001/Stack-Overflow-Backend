package dev.stackoverflow.controller;

import dev.stackoverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;


}
