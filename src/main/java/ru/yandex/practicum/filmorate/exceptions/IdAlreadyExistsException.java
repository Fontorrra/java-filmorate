package ru.yandex.practicum.filmorate.exceptions;

public class IdAlreadyExistsException extends AllControllerExceptions {
    public IdAlreadyExistsException(String message) {
        super(message);
    }
}
