package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws IdDoesNotExistsException {
        log.info("Received PUT request to endpoint /users with body {}", user);
        if (userService.updateUser(user)) log.info("User {} updated successfully", user);
        return user;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws IdAlreadyExistsException {
        log.info("Received POST request to endpoint /users with body {}", user);
        user = userService.createUser(user);
        log.info("User {} added successfully", user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Received GET request to endpoint /users");
        return userService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Long id,
                            @PathVariable Long friendId) {
        log.info("Received PUT request to endpoint /users/{}/friends/{}", id, friendId);
        if (userService.addFriend(id, friendId))
        {
            log.info("Friend successfully added");
            return "Friend successfully added";
        }
        else return "Something went wrong";
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Long id,
                               @PathVariable Long friendId) {
        log.info("Received PUT request to endpoint /users/{}/friends/{}", id, friendId);
        if (userService.deleteFriend(id, friendId)) {
            log.info("Friend successfully deleted");
            return "Friend successfully deleted";
        }
        else return "Something went wrong";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("Received GET request to endpoint /users/{}", id);
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable Long id) {
        log.info("Received GET request to endpoint /users/{}/friends", id);
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id,
                                             @PathVariable Long otherId) {
        log.info("Received GET request to endpoint /users/{}/friends/common/{}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}