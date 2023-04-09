package dev.stackoverflow.service;

import dev.stackoverflow.model.PostVoter;
import dev.stackoverflow.model.User;
import dev.stackoverflow.repository.PostVoterRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional @RequiredArgsConstructor
public class PostVoterService {

    @Autowired
    private final PostVoterRepository postVoterRepository;

    public List<PostVoter> getAllByPostId(@NonNull Long postId) {
        return postVoterRepository.findAllByPostId(postId);
    }

    public PostVoter save(@NonNull PostVoter postVoter) {
        return postVoterRepository.save(postVoter);
    }

    public void deleteAllByPostId(@NonNull Long postId) {
        postVoterRepository.deleteAllByPostId(postId);
    }
}
