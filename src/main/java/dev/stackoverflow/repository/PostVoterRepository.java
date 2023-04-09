package dev.stackoverflow.repository;

import dev.stackoverflow.model.PostVoter;
import dev.stackoverflow.model.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface PostVoterRepository extends JpaRepositoryImplementation<PostVoter, Long> {
    List<PostVoter> findAllByPostId(Long postId);
    List<PostVoter> findAllByUser(User user);
    boolean existsPostVoterByUser(User user);

    void deleteAllByPostId(Long postId);

}
