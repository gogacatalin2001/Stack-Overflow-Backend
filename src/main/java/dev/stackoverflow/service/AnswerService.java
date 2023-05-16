package dev.stackoverflow.service;

import dev.stackoverflow.exception.QuestionNotFoundException;
import dev.stackoverflow.model.Answer;
import dev.stackoverflow.repository.AnswerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    @Autowired
    private final AnswerRepository answerRepository;

    public Answer get(@NonNull Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElse(null);
    }

    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    public Answer save(@NonNull Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer update(@NonNull Answer answer, @NonNull Long id) {
        if (answerRepository.existsById(id)) {
            answer.setId(id);
            return answerRepository.save(answer);
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public void delete(@NonNull Long id) {
        answerRepository.deleteById(id);
    }
}
