package dev.stackoverflow.exception;

public class EmptyQuestionFieldsException extends RuntimeException{
    public EmptyQuestionFieldsException(Long id) {
        super("Question " + id +": Question title and text cannot be empty and the question must have at least one tag!");
    }
}
