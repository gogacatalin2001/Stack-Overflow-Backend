package dev.stackoverflow.exception;

public class EmptyAnswerFieldsException extends RuntimeException {
    public EmptyAnswerFieldsException(Long id) {
        super("Question" + id + ": answer text cannot be empty!");
    }
}
