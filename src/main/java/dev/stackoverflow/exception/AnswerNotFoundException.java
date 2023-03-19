package dev.stackoverflow.exception;

import java.awt.*;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super("Could not find answer with id: " + id);
    }
}
