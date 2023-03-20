package dev.stackoverflow.service;

import dev.stackoverflow.exception.AnswerNotFoundException;
import dev.stackoverflow.exception.QuestionNotFoundException;
import dev.stackoverflow.exception.UserNotFoundException;
import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.Tag;
import dev.stackoverflow.model.User;
import dev.stackoverflow.repository.QuestionRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    public Question getQuestion(@NonNull Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public Question saveQuestion(@NonNull Question question) {
        List<Tag> tags = question.getTags();
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            if (tagService.existsByText(tag.getText())) {
                tags.remove(tag);
                tags.add(tagService.getByText(tag.getText()));
            }
        }
        question.setTags(tags);
        return questionRepository.save(question);
    }

    public Question updateQuestion(@NonNull Question question, @NonNull Long id) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(@NonNull Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            Question questionInstance = question.get();
            List<Answer> answers = questionInstance.getAnswers();
            for (Answer a : answers) {
                answerService.deleteAnswer(a.getId());
            }
            questionRepository.deleteById(questionInstance.getId());
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public Question addAnswer(@NonNull Long id, @NonNull Answer answer) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            Question questionInstance = question.get();
            questionInstance.addAnswer(answer);
            questionRepository.save(questionInstance);
            answerService.saveAnswer(answer);
            return questionInstance;
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public Question deleteAnswer(@NonNull Long questionId, @NonNull Long answerId) {
        Optional<Question> question = questionRepository.findById(questionId);
        Answer answer = answerService.getAnswer(answerId);
        if (question.isPresent()) {
            if (answer != null) {
                Question questionInstance = question.get();
                questionInstance.deleteAnswer(answer);
                questionRepository.save(questionInstance);
                answerService.deleteAnswer(answer.getId());
                return questionInstance;
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public List<Question> saveQuestions(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }
}
