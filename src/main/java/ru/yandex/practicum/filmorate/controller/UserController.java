package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;


@Slf4j
@RestController
@RequestMapping("/users")

public class UserController {

    HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws IdAlreadyExistsException {
        if (user.getId() == null) {
            while(users.containsKey(id)) {
                id++;
            }
            user.setId(id);
            id++;
        }
        else if (users.containsKey(user.getId())) {
            throw new IdAlreadyExistsException();
        }
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> getFilms() {
        return users.values();

    }
}
