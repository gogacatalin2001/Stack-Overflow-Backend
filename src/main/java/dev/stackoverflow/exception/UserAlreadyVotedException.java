package dev.stackoverflow.exception;

public class UserAlreadyVotedException extends RuntimeException {
    public UserAlreadyVotedException(Long id) {
        super("The user with ID:" + id + " already rated this post");
    }
}
