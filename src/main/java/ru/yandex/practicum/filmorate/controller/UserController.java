package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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
        log.info("Received POST request to endpoint /users with body {}", user);
        setUserName(user);
        if (user.getId() == null) {
            while(users.containsKey(id)) {
                id++;
            }
            user.setId(id);
            id++;
        }
        else if (users.containsKey(user.getId())) {
            log.warn("User with ID {} already exists", user.getId());
            throw new IdAlreadyExistsException("User with this ID  already exists");
        }
        users.put(user.getId(), user);
        log.info("User {} added successfully", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws IdDoesNotExistsException {
        log.info("Received PUT request to endpoint /users with body {}", user);
        setUserName(user);
        if (!users.containsKey(user.getId())) {
            log.warn("User with ID {} does not exist", user.getId());
            throw new IdDoesNotExistsException("User with this ID does not exist");
        }
        users.put(user.getId(), user);
        log.info("User {} updated successfully", user);
        return user;
    }

    @GetMapping
    public Collection<User> getFilms() {
        log.info("Received GET request to endpoint /users");
        return users.values();
    }

    private void setUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }
}
