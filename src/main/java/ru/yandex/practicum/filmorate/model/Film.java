package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.*;

@Data
@Builder
public class Film {

    private Integer id;

    @NotNull(message = "Имя не должно быть null")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotNull(message = "Описание не должно быть null")
    @Size(max = 200, message = "Максимальная длина описания 200")
    private String description;

    @NotNull(message = "Дата релиза не должна быть null")
    private LocalDate releaseDate;

    @NotNull(message = "Продолжительность не должна быть null")
    private int duration;
}
