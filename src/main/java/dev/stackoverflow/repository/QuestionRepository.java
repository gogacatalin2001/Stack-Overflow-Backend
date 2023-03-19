package dev.stackoverflow.repository;

import dev.stackoverflow.model.Question;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepositoryImplementation<Question, Long> {
}
