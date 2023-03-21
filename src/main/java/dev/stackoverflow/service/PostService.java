package dev.stackoverflow.service;

import dev.stackoverflow.exception.AnswerNotFoundException;
import dev.stackoverflow.exception.QuestionNotFoundException;
import dev.stackoverflow.model.Answer;
import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.QuestionTag;
import dev.stackoverflow.model.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionTagService questionTagService;

    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    public Question getQuestion(@NonNull Long id) {
        return questionService.getQuestion(id);
    }

    public Question saveQuestion(@NonNull Question question, @NonNull List<Tag> tags) {
        for (Tag tag : tags) {
            QuestionTag questionTag;
            if (tagService.existsByText(tag.getText())) {
                questionTag = new QuestionTag(question, tagService.getByText(tag.getText()));
            } else {
                questionTag = new QuestionTag(question, tag);
            }
            questionTagService.saveQuestionTag(questionTag);
        }
        return questionService.saveQuestion(question);

    }

    public Question updateQuestion(@NonNull Question question, @NonNull Long id) {
        // TODO modify the answers and tags of the question too
        Question oldQuestion = questionService.getQuestion(id);
        if (oldQuestion != null) {
            // TODO ???
            return questionService.updateQuestion(question, id);
        } else {
            throw new QuestionNotFoundException(id);
        }

    }

    public void deleteQuestion(@NonNull Long id) {
        Question question = questionService.getQuestion(id);
        if (question != null) {
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                answerService.deleteAnswer(answer.getId());
            }
            questionTagService.deleteAllByQuestionId(id);
            questionService.deleteQuestion(id);
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public List<Answer> getAnswers() {
        return answerService.getAnswers();
    }

    public Answer getAnswer(@NonNull Long id) {
        return answerService.getAnswer(id);
    }

    public Answer saveAnswer(@NonNull Answer answer, @NonNull Long questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            question.addAnswer(answer);
            questionService.updateQuestion(question, questionId);
            return answerService.saveAnswer(answer);
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public Answer updateAnswer(@NonNull Answer newAnswer, @NonNull Long answerId, @NonNull Long questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            Answer oldAnswer = answerService.getAnswer(answerId);
            if (oldAnswer != null) {
                newAnswer.setId(answerId);
                question.deleteAnswer(oldAnswer);
                question.addAnswer(newAnswer);
                questionService.updateQuestion(question, questionId);
                return answerService.updateAnswer(newAnswer, answerId);
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public Question deleteAnswer(@NonNull Long questionId, @NonNull Long answerId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            Answer answer = answerService.getAnswer(answerId);
            if (answer != null) {
                question.deleteAnswer(answer);
                answerService.deleteAnswer(answerId);
                questionService.updateQuestion(question, questionId);
                return question;
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }
}
