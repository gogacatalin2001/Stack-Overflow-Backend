package dev.stackoverflow.repository;

import dev.stackoverflow.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepositoryImplementation<Tag, Long> {

    boolean existsByText(String text);
    Tag findByText(String text);
}
