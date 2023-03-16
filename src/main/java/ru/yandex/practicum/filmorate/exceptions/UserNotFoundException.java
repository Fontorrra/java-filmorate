package ru.yandex.practicum.filmorate.exceptions;

import ru.yandex.practicum.filmorate.model.User;

public class UserNotFoundException extends NotFoundExceptions {
    public UserNotFoundException(String message) {
        super(message);
    }
}
