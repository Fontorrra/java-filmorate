package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;

    @NotNull(message = "email не может быть null")
    @Email(message = "Неправильный формат email")
    private String email;

    @NotNull(message = "Логин не должен быть null")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    private String name;

    @PastOrPresent(message = "День рождения не может быть в будущем")
    private LocalDate birthday;

    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.PRIVATE)
    Set<Long> friends;

    @Builder
    User(final Long id, final String email, final String login, final String name, final LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashSet<>();
    }

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void deleteFriend(Long id) {
        friends.remove(id);
    }

    public Collection<Long> getFriend() {
        return friends;
    } //
}