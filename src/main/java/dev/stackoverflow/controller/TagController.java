package dev.stackoverflow.controller;

import dev.stackoverflow.model.Tag;
import dev.stackoverflow.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor@CrossOrigin(origins = "http://localhost:3000",allowedHeaders = "*", allowCredentials = "true")
public class TagController {

    @Autowired
    private final TagService tagService;

    @GetMapping("/tags/all")
    @ResponseBody
    public List<Tag> getAll() {
        return tagService.getAll();
    }
}
