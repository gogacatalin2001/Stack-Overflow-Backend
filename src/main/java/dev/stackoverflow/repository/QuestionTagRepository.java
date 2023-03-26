package dev.stackoverflow.repository;

import dev.stackoverflow.model.QuestionTag;
import lombok.NonNull;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface QuestionTagRepository extends JpaRepositoryImplementation<QuestionTag, Long> {

    List<QuestionTag> findAllByQuestionId(Long questionId);
    List<QuestionTag> findAllByTagId(Long tagId);

    void deleteAllByTagId(Long tagId);

    void deleteAllByQuestionId(Long questionId);
}
