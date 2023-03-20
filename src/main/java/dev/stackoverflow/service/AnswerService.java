package dev.stackoverflow.service;

import dev.stackoverflow.model.Answer;
import dev.stackoverflow.repository.AnswerRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer getAnswer(@NonNull Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElse(null);
    }

    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }

    public Answer saveAnswer(@NonNull Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> saveAnswers(@NonNull List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }

    public Answer updateAnswer(@NonNull Answer answer, @NonNull Long id) {
        return answerRepository.save(answer);
    }

    public void deleteAnswer(@NonNull Long id) {
        answerRepository.deleteById(id);
    }
}
