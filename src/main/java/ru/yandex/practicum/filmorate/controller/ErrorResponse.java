package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String path;
    private final LocalDateTime timestamp;
}
