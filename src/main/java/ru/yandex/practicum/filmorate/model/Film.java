package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;

import javax.validation.constraints.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
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
    private Set<Long> usersLiked;

    @Builder
    Film(final Long id, final String name, final String description, final LocalDate releaseDate, final int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        usersLiked = new HashSet<>();
    }

    public void addLike(Long id) {
        if (usersLiked.contains(id)) {
            log.warn("Like already exist");
            throw new IdDoesNotExistsException("Like already exist");
        }
        usersLiked.add(id);
    }

    public void removeLike(Long id) {
        if (!usersLiked.contains(id)) {
            log.warn("Like does not exist");
            throw new IdDoesNotExistsException("Like does not exist");
        }
        usersLiked.remove(id);
    }

    public Integer getLikes() {
        return usersLiked.size();
    }

}