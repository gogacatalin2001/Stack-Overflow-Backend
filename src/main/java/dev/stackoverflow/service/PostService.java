package dev.stackoverflow.service;

import dev.stackoverflow.exception.*;
import dev.stackoverflow.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<QuestionTagWrapper> getQuestions() {
        List<QuestionTagWrapper> questionTagWrappers = new ArrayList<>();
        List<Question> questions = questionService.getQuestions();
        for (Question question : questions) {
            List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(question.getId());
            List<Tag> tags = new ArrayList<>();
            for (QuestionTag qt : questionTags) {
                tags.add(qt.getTag());
            }
            questionTagWrappers.add(new QuestionTagWrapper(question, tags));
        }
        return questionTagWrappers;
    }

    public QuestionTagWrapper getQuestion(@NonNull Long questionId) {
        return getQuestionTagWrapper(questionId);
    }

    public QuestionTagWrapper saveQuestion(@NonNull Question question, @NonNull List<Tag> tags, @NonNull Long userId) {
        if (!question.getText().isEmpty() && !question.getTitle().isEmpty() && !tags.isEmpty()) {
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
                questionService.updateQuestion(savedQuestion, savedQuestion.getId());
                return getQuestionTagWrapper(savedQuestion.getId());
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new EmptyQuestionFieldsException(question.getId());
        }
    }

    public Question updateQuestion(@NonNull QuestionTagWrapper questionTagWrapper, @NonNull Long questionId, @NonNull Long userId) {
        Question question = questionTagWrapper.getQuestion();
        List<Tag> tags = questionTagWrapper.getTags();
        if (!question.getText().isEmpty() && !question.getTitle().isEmpty() && !tags.isEmpty()) {
            User user = userService.getUserById(userId);
            if (user != null) {
                Question oldQuestion = questionService.getQuestion(questionId);
                if (oldQuestion != null) {
                    question.setUser(user);
                    List<Answer> answers = oldQuestion.getAnswers();
                    List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(questionId);
                    for (Answer answer : answers) {
                        answer.setQuestion(question);
                    }
                    tagService.saveTags(tags);
                    for (Tag tag : tags) {
                        questionTagService.save(new QuestionTag(question, tag));
                    }
                    Question newQuestion = questionService.updateQuestion(question, questionId);
                    for (QuestionTag questionTag : questionTags) {
                        questionTag.setQuestion(newQuestion);
                        questionTagService.save(questionTag);
                    }
                    return newQuestion;
                } else {
                    throw new QuestionNotFoundException(questionId);
                }
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new EmptyQuestionFieldsException(questionId);
        }
    }

    public Question updateQuestionVotes(@NonNull Long questionId, @NonNull Long userId, @NonNull Integer amount) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                if (!user.equals(question.getUser())) {
                    List<PostVoter> postVotes = postVoterService.getAllByPostId(questionId);
                    boolean alreadyVoted = false;
                    for (PostVoter postVoter : postVotes) {
                        if (postVoter.getUser().equals(user)) {
                            alreadyVoted = true;
                            break;
                        }
                    }
                    if (!alreadyVoted) {
                        PostVoter postVoter = new PostVoter(question, user);
                        if (amount == 1) {
                            postVoterService.save(postVoter);
                            userService.updateUserScore(userId, 2.5);
                            question.setVoteCount(amount + question.getVoteCount());
                            return questionService.updateQuestion(question, questionId);
                        } else if (amount == -1) {
                            postVoterService.save(postVoter);
                            userService.updateUserScore(userId, -1.5);
                            question.setVoteCount(amount + question.getVoteCount());
                            return questionService.updateQuestion(question, questionId);
                        }
                    } else {
                        throw new UserAlreadyVotedException(userId);
                    }
                } else {
                    throw new AuthorVotedOwnPostException(questionId);
                }
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
        return question;
    }

    public void deleteQuestion(@NonNull Long questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                answerService.deleteAnswer(answer.getId());
            }
            questionTagService.deleteAllByQuestionId(questionId);
            questionService.deleteQuestion(questionId);
        } else {
            throw new QuestionNotFoundException(questionId);
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

    public Answer getAnswer(@NonNull Long answerId) {
        return answerService.getAnswer(answerId);
    }

    public Answer saveAnswer(@NonNull Answer answer, @NonNull Long questionId, @NonNull Long userId) {
        if (!answer.getText().isEmpty()) {

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
        } else {
            throw new EmptyAnswerFieldsException(questionId);
        }
    }

    public Answer updateAnswer(@NonNull Answer newAnswer, @NonNull Long questionId, @NonNull Long answerId, @NonNull Long userId) {
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

    public Answer updateAnswerVotes(@NonNull Long questionId, @NonNull Long answerId, @NonNull Long userId, @NonNull Integer amount) {
        Question question = questionService.getQuestion(questionId);
        if (question != null) {
            Answer answer = answerService.getAnswer(answerId);
            if (answer != null) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    if (!user.equals(answer.getUser())) {
                        List<PostVoter> postVotes = postVoterService.getAllByPostId(answerId);
                        boolean alreadyVoted = false;
                        for (PostVoter postVoter : postVotes) {
                            if (postVoter.getUser().equals(user)) {
                                alreadyVoted = true;
                                break;
                            }
                        }
                        if (!alreadyVoted) {
                            PostVoter postVoter = new PostVoter(answer, user);
                            if (amount == 1) {
                                postVoterService.save(postVoter);
                                userService.updateUserScore(userId, 5.0);
                                answer.setVoteCount(amount + answer.getVoteCount());
                                answerService.updateAnswer(answer, answerId);
                            } else if (amount == -1) {
                                postVoterService.save(postVoter);
                                userService.updateUserScore(userId, -2.5);
                                answer.setVoteCount(amount + answer.getVoteCount());
                                answerService.updateAnswer(answer, answerId);
                            }
                        } else {
                            throw new UserAlreadyVotedException(userId);
                        }
                    }
                    return answer;
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

    private QuestionTagWrapper getQuestionTagWrapper(@NonNull Long questionId) {
        Question question = questionService.getQuestion(questionId);
        List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(question.getId());
        List<Tag> tags = new ArrayList<>();
        for (QuestionTag questionTag : questionTags) {
            tags.add(questionTag.getTag());
        }
        return new QuestionTagWrapper(question, tags);
    }
}
