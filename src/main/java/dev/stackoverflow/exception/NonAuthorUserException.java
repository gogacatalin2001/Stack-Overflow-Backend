package dev.stackoverflow.exception;

public class NonAuthorUserException extends RuntimeException {
    public NonAuthorUserException() {
        super("Only the author of the post or a moderator can edit id");
    }
}
