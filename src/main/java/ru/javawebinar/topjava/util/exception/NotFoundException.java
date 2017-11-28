package ru.javawebinar.topjava.util.exception;

/**
 * Created by grh on 11/28/17.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
