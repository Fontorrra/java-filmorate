package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exceptions.NumOfLikesIsZeroException;

import javax.validation.constraints.*;
import java.time.*;

@Data
public class Film {

    private Long id;

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

    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.PRIVATE)
    private int likes;

    @Builder
    Film(final Long id, final String name, final String description, final LocalDate releaseDate, final int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        likes = 0;
    }

    public void addLike() {
        likes++;
    }

    public void removeLike() throws NumOfLikesIsZeroException {
        if (likes == 0) {
            throw new NumOfLikesIsZeroException("Невозможно удалить лайк");
        }
        likes--;
    }

}
