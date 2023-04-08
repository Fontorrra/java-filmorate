package ru.yandex.practicum.filmorate.exceptions;

public class UserNotFoundException extends NotFoundExceptions {
    public UserNotFoundException(String message) {
        super(message);
    }
}