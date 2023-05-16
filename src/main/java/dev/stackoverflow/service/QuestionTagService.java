package dev.stackoverflow.service;

import dev.stackoverflow.model.Question;
import dev.stackoverflow.model.QuestionTag;
import dev.stackoverflow.model.Tag;
import dev.stackoverflow.repository.QuestionTagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionTagService {

    @Autowired
    private final QuestionTagRepository questionTagRepository;

    public QuestionTag save(@NonNull QuestionTag questionTag) {
        List<QuestionTag> questionTags = getAllByQuestionId(questionTag.getQuestion().getId());
        Tag tag = questionTag.getTag();
        Question question = questionTag.getQuestion();
        for (QuestionTag qt : questionTags) {
            if (qt.getTag().equals(tag) || qt.getQuestion().equals(question)) {
                return qt;
            }
        }
        return questionTagRepository.save(questionTag);
    }

    public List<QuestionTag> getAllByQuestionId(@NonNull Long questionId) {
        return questionTagRepository.findAllByQuestionId(questionId);
    }

    public List<QuestionTag> getAllByTagId(@NonNull Long tagId) {
        return questionTagRepository.findAllByTagId(tagId);
    }

    public void deleteAllByTagId(@NonNull Long tagId) {
        questionTagRepository.deleteAllByTagId(tagId);
    }

    public void deleteAllByQuestionId(@NonNull Long questionId) {
        questionTagRepository.deleteAllByQuestionId(questionId);
    }
}
