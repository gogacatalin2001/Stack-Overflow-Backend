package dev.stackoverflow.service;

import dev.stackoverflow.exception.AnswerNotFoundException;
import dev.stackoverflow.exception.QuestionNotFoundException;
import dev.stackoverflow.exception.UserAlreadyVotedException;
import dev.stackoverflow.exception.UserNotFoundException;
import dev.stackoverflow.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private final QuestionService questionService;
    @Autowired
    private final AnswerService answerService;
    @Autowired
    private final TagService tagService;
    @Autowired
    private final QuestionTagService questionTagService;
    @Autowired
    private final PostVoterService postVoterService;
    @Autowired
    private final UserService userService;

    public List<Question> getQuestions() {
        return questionService.getQuestions()
                .stream()
                .sorted()
                .toList();
    }

    public Question getQuestion(@NonNull Long id) {
        return questionService.getQuestion(id);
    }

    public Question saveQuestion(@NonNull Question question, @NonNull List<Tag> tags, @NonNull Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            question.setUser(user);
            question.setVoteCount(0);
            Question savedQuestion = questionService.saveQuestion(question);
            for (Tag tag : tags) {
                QuestionTag questionTag;
                if (tagService.existsByText(tag.getText())) {
                    questionTag = new QuestionTag(savedQuestion, tagService.getByText(tag.getText()));
                } else {
                    questionTag = new QuestionTag(savedQuestion, tagService.saveTag(tag));
                }
                questionTagService.save(questionTag);
            }
            return questionService.updateQuestion(savedQuestion, savedQuestion.getId());
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    public Question updateQuestion(@NonNull Question question, @NonNull Long id) {
        Question oldQuestion = questionService.getQuestion(id);
        if (oldQuestion != null) {
            List<Answer> answers = oldQuestion.getAnswers();
            for (Answer answer : answers) {
                question.addAnswer(answer);
                answer.setQuestion(question);
            }
            Question newQuestion = questionService.updateQuestion(question, id);
            List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(id);
            for (QuestionTag questionTag : questionTags) {
                questionTag.setQuestion(question);
                questionTagService.save(questionTag);
            }
            return newQuestion;
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    public Question updateQuestionVotes(@NonNull Long questionId, @NonNull Long userId, @NonNull Integer amount) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                List<PostVoter> postVotes = postVoterService.getAllByPostId(questionId);
                List<PostVoter> userVotes = postVotes.stream().filter(vote -> vote.getUser().equals(user)).toList();
                if (userVotes.isEmpty()) {
                    postVoterService.save(new PostVoter(question, user));
                    int voteCount = question.getVoteCount();
                    question.setVoteCount(amount + voteCount);
                    questionService.updateQuestion(question, questionId);
                } else {
                    throw new UserAlreadyVotedException(userId);
                }
                return question;
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
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

    public List<Answer> getAnswersByQuestionId(@NonNull Long questionId) {
        Question question = questionService.getQuestion(questionId);
        List<Answer> answers;
        if (question != null) {
            List<Answer> allAnswers = answerService.getAnswers();
            answers = allAnswers
                    .stream()
                    .filter(answer -> answer.getQuestion().equals(question))
                    .sorted()
                    .toList();
            return answers;
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public Answer getAnswer(@NonNull Long id) {
        return answerService.getAnswer(id);
    }

    public Answer saveAnswer(@NonNull Answer answer, @NonNull Long questionId, @NonNull Long userId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                answer.setUser(user);
                answer.setVoteCount(0);
                answer.setQuestion(question);
                Answer savedAnswer = answerService.saveAnswer(answer);
                question.addAnswer(savedAnswer);
                questionService.updateQuestion(question, questionId);
                return savedAnswer;
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public Answer updateAnswer(@NonNull Answer newAnswer, @NonNull Long answerId, @NonNull Long questionId, @NonNull Long userId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            Answer oldAnswer = answerService.getAnswer(answerId);
            if (oldAnswer != null) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    newAnswer.setUser(user);
                    newAnswer.setId(answerId);
                    newAnswer.setQuestion(question);
                    question.deleteAnswer(oldAnswer);
                    question.addAnswer(newAnswer);
                    questionService.updateQuestion(question, questionId);
                    return answerService.updateAnswer(newAnswer, answerId);
                } else {
                    throw new UserNotFoundException(userId);
                }
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public Answer updateAnswerVotes(@NonNull Long answerId, @NonNull Long userId, @NonNull Integer amount) {
        Answer answer = answerService.getAnswer(answerId);
        if (answer != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                List<PostVoter> postVotes = postVoterService.getAllByPostId(answerId);
                List<PostVoter> userVotes = postVotes.stream().filter(vote -> vote.getUser().equals(user)).toList();
                if (userVotes.isEmpty()) {
                    postVoterService.save(new PostVoter(answer, user));
                    int voteCount = answer.getVoteCount();
                    answer.setVoteCount(amount + voteCount);
                    answerService.updateAnswer(answer, answerId);
                } else {
                    throw new UserAlreadyVotedException(userId);
                }
                return answer;
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new AnswerNotFoundException(answerId);
        }
    }

    public void deleteAnswer(@NonNull Long questionId, @NonNull Long answerId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            Answer answer = answerService.getAnswer(answerId);
            if (answer != null) {
                question.deleteAnswer(answer);
                answerService.deleteAnswer(answerId);
                questionService.updateQuestion(question, questionId);
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }
}
