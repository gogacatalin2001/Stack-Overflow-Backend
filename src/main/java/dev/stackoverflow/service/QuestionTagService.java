package dev.stackoverflow.service;

import dev.stackoverflow.model.QuestionTag;
import dev.stackoverflow.repository.QuestionTagRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTagService {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    public QuestionTag saveQuestionTag(@NonNull QuestionTag questionTag) {
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
