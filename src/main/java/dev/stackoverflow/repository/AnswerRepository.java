package dev.stackoverflow.repository;

import dev.stackoverflow.model.Answer;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepositoryImplementation<Answer, Long> {
}
