package dev.stackoverflow.service;

import dev.stackoverflow.exception.QuestionNotFoundException;
import dev.stackoverflow.model.*;
import dev.stackoverflow.repository.QuestionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service @Transactional @RequiredArgsConstructor
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    public Question getQuestion(@NonNull Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public Question saveQuestion(@NonNull Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(@NonNull Question question, @NonNull Long id) {
        if (questionRepository.existsById(id)) {
            question.setId(id);
            return questionRepository.save(question);
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public void deleteQuestion(@NonNull Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            questionRepository.deleteById(question.get().getId());
        } else {
            throw new QuestionNotFoundException(id);
        }
    }
}
