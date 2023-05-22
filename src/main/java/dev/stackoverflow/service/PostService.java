package dev.stackoverflow.service;

import dev.stackoverflow.exception.*;
import dev.stackoverflow.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private final ImageService imageService;

    public List<QuestionWrapper> getQuestions() {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = questionService.getAll();
        for (Question question : questions) {
            List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(question.getId());
            List<Tag> tags = new ArrayList<>();
            for (QuestionTag qt : questionTags) {
                tags.add(qt.getTag());
            }
            questionWrappers.add(new QuestionWrapper(question, tags));
        }
        return questionWrappers;
    }

    public QuestionWrapper getQuestion(@NonNull Long questionId) {
        return getQuestionWrapper(questionId);
    }

    public QuestionWrapper saveQuestion(@NonNull Question question, @NonNull List<Tag> tags, @NonNull Long userId, Long imageId) {
        if (!question.getText().isEmpty() && !question.getTitle().isEmpty() && !tags.isEmpty()) {
            User user = userService.getUserById(userId);
            if (user != null) {
                Image image = imageService.getById(imageId);
                if (image != null) {
                    question.setImage(image);
                    System.out.println(question.getImage());
                }
                question.setUser(user);
                question.setVoteCount(0);
                Question savedQuestion = questionService.save(question);
                for (Tag tag : tags) {
                    questionTagService.save(new QuestionTag(question, tagService.save(tag)));
                }
                questionService.update(savedQuestion, savedQuestion.getId());
                return getQuestionWrapper(savedQuestion.getId());
            } else {
                throw new UserNotFoundException(userId);
            }
        } else {
            throw new EmptyQuestionFieldsException(question.getId());
        }
    }

    public QuestionWrapper updateQuestion(@NonNull QuestionWrapper questionWrapper, Long imageId, @NonNull Long questionId, @NonNull Long userId) throws IOException {
        Question question = questionWrapper.getQuestion();
        List<Tag> tags = questionWrapper.getTags();
        if (!question.getText().isEmpty() && !question.getTitle().isEmpty() && !tags.isEmpty()) {
            User user = userService.getUserById(userId);
            if (user != null) {
                Question oldQuestion = questionService.get(questionId);
                if (oldQuestion != null) {
                    if (user.equals(oldQuestion.getUser()) || user.getRole().equals(Role.MODERATOR)) {
                        Image image = imageService.getById(imageId);
                        if (image != null) {
                            question.setImage(image);
                        }
                        questionService.update(question, questionId);
                        for (Tag tag : tags) {
                            questionTagService.save(new QuestionTag(question, tagService.save(tag)));
                        }
                        return getQuestionWrapper(questionId);
                    } else {
                        throw new NonAuthorUserException();
                    }
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

    public QuestionWrapper updateQuestionVotes(@NonNull Long questionId, @NonNull Long userId, @NonNull Integer amount) {
        Question question = questionService.get(questionId);
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
                            questionService.update(question, questionId);
                        } else if (amount == -1) {
                            postVoterService.save(postVoter);
                            userService.updateUserScore(userId, -1.5);
                            question.setVoteCount(amount + question.getVoteCount());
                            questionService.update(question, questionId);
                        }
                        return getQuestionWrapper(questionId);
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
    }

    public void deleteQuestion(@NonNull Long questionId) {
        Question question = questionService.get(questionId);
        if (question != null) {
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                answerService.delete(answer.getId());
            }
            questionTagService.deleteAllByQuestionId(questionId);
            questionService.delete(questionId);
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    public List<Answer> getAnswers() {
        return answerService.getAll();
    }

    public List<Answer> getAnswersByQuestionId(@NonNull Long questionId) {
        Question question = questionService.get(questionId);
        List<Answer> answers;
        if (question != null) {
            List<Answer> allAnswers = answerService.getAll();
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
        return answerService.get(answerId);
    }

    public Answer saveAnswer(@NonNull Answer answer, Long imageId, @NonNull Long questionId, @NonNull Long userId) {
        if (!answer.getText().isEmpty()) {
            Question question = questionService.get(questionId);
            if (question != null) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    Image image = imageService.getById(imageId);
                    if (image != null) {
                        answer.setImage(image);
                    }
                    answer.setUser(user);
                    answer.setVoteCount(0);
                    answer.setQuestion(question);
                    Answer savedAnswer = answerService.save(answer);
                    question.addAnswer(savedAnswer);
                    questionService.update(question, questionId);
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

    public Answer updateAnswer(@NonNull Answer answer, Long imageId, @NonNull Long questionId, @NonNull Long answerId, @NonNull Long userId) {
        Question question = questionService.get(questionId);
        if (question != null) {
            Answer oldAnswer = answerService.get(answerId);
            if (oldAnswer != null) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    if (user.equals(oldAnswer.getUser()) || user.getRole().equals(Role.MODERATOR)) {
                        Image image = imageService.getById(imageId);
                        if (image != null) {
                            answer.setImage(image);
                        }
                        answer.setQuestion(question);
                        return answerService.update(answer, answerId);
                    } else {
                        throw new NonAuthorUserException();
                    }
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
        Question question = questionService.get(questionId);
        if (question != null) {
            Answer answer = answerService.get(answerId);
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
                                answerService.update(answer, answerId);
                            } else if (amount == -1) {
                                postVoterService.save(postVoter);
                                userService.updateUserScore(userId, -2.5);
                                answer.setVoteCount(amount + answer.getVoteCount());
                                answerService.update(answer, answerId);
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

    public void deleteAnswer(@NonNull Long questionId, @NonNull Long answerId, @NonNull Long userId) {
        Question question = questionService.get(questionId);
        if (question != null) {
            Answer answer = answerService.get(answerId);
            if (answer != null) {
                User user = userService.getUserById(userId);
                if (user != null) {
                    if (user.equals(answer.getUser()) || user.getRole().equals(Role.MODERATOR)) {
                        question.deleteAnswer(answer);
                        answerService.delete(answerId);
                        questionService.update(question, questionId);
                    }
                }
            } else {
                throw new AnswerNotFoundException(answerId);
            }
        } else {
            throw new QuestionNotFoundException(questionId);
        }
    }

    private QuestionWrapper getQuestionWrapper(@NonNull Long questionId) {
        Question question = questionService.get(questionId);
        List<QuestionTag> questionTags = questionTagService.getAllByQuestionId(question.getId());
        List<Tag> tags = new ArrayList<>();
        for (QuestionTag questionTag : questionTags) {
            tags.add(questionTag.getTag());
        }
        return new QuestionWrapper(question, tags);
    }
}
