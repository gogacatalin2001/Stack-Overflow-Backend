package dev.stackoverflow.exception;

public class AuthorVotedOwnPostException extends RuntimeException {
    public AuthorVotedOwnPostException(Long id) {
        super("Post " + id + ": The author cannot vote his own post!");
    }
}
