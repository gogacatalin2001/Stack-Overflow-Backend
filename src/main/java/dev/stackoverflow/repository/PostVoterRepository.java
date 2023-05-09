package dev.stackoverflow.repository;

import dev.stackoverflow.model.PostVoter;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface PostVoterRepository extends JpaRepositoryImplementation<PostVoter, Long> {
    List<PostVoter> findAllByPostId(Long postId);
    void deleteAllByPostId(Long postId);
}
