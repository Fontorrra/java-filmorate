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
    private int id = 1;



    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws IdAlreadyExistsException {
        setUserName(user);

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
    public User updateUser(@Valid @RequestBody User user) throws IdDoesNotExistsException {
        setUserName(user);
        if (!users.containsKey(user.getId())) throw new IdDoesNotExistsException();
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> getFilms() {
        return users.values();
    }

    private void setUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }
}
