package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import java.util.*;

@Service
@Slf4j
public class UserService {

    private final InMemoryUserStorage userStorage;

    UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        setUserName(user);
        if (user.getId() == null) {
            user.setId(userStorage.getIdForUser());
        }
        return userStorage.addUser(user);
    }

    public boolean updateUser(User user) {
        setUserName(user);
        return userStorage.updateUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public boolean addFriend(Long id, Long friendId) {
        if (id == null || friendId == null) {
            log.warn("id must be not null");
            throw new UserNotFoundException("id can`t be null");
        }
        return userStorage.addFriend(id, friendId);
    }

    public boolean deleteFriend(Long id, Long friendId) {
        if (id == null || friendId == null) {
            log.warn("id must be not null");
            throw new UserNotFoundException("id can`t be null");
        }
        return userStorage.deleteFriend(id, friendId);
    }

    public Collection<User> getUserFriends(Long id) {
        if (id == null) {
            log.warn("id must be not null");
            throw new UserNotFoundException("id can`t be null");
        }
        return userStorage.getUserFriends(id);
    }

    public User getUser(Long id) {
        if (id == null || id <= 0) {
            log.warn("id must be not null and positive");
            throw new IdDoesNotExistsException("id can`t be null or not positive");
        }
        return userStorage.getUser(id);
    }

    public Collection<User> getCommonFriends(Long id, Long otherId) {
        if (id == null || otherId == null) {
            log.warn("id must be not null");
            throw new UserNotFoundException("id can`t be null");
        }
        HashSet<User> friends = new HashSet<>(userStorage.getUserFriends(id));
        HashSet<User> otherFriends = new HashSet<>(userStorage.getUserFriends(otherId));
        friends.retainAll(otherFriends);
        return friends;
    }

    public List<User> getAllFriends() {
        return null;
    }

    private void setUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }

}
