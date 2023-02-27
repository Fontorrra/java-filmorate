package ru.yandex.practicum.filmorate.exceptions;

public class IdDoesNotExistsException extends AllControllerExceptions {
    public IdDoesNotExistsException(String message) {
        super(message);
    }
}
