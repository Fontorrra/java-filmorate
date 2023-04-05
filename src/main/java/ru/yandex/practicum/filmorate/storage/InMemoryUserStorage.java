package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    HashMap<Long, User> users = new HashMap<>();
    private long id = 1;


    @Override
    public User addUser(User user) {
        if (users.containsKey(user.getId())) {
            log.warn("User with ID already exists");
            throw new IdAlreadyExistsException("User with this ID already exists");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("User with ID {} does not exist", user.getId());
            throw new IdDoesNotExistsException("User with this ID does not exist");
        }
        users.put(user.getId(), user);
        return true;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public boolean addFriend(long id, long friendId) {
        User user = users.get(id);
        User friend = users.get(friendId);
        if (user == null) {
            log.warn("User with this id = {} does not exist", id);
            throw new UserNotFoundException("User with this id " + id + " does not exist");
        }

        if (friend == null) {
            log.warn("User with this id = {} does not exist", friendId);
            throw new UserNotFoundException("User with this id " + friendId + " does not exist");
        }

        user.addFriend(friendId);
        friend.addFriend(id);
        return true;
    }

    @Override
    public boolean deleteFriend(long id, long friendId) {
        User user = users.get(id);
        User friend = users.get(friendId);
        if (user == null) {
            log.warn("User with this id = {} does not exist", id);
            throw new UserNotFoundException("User with this id " + id + " does not exist");
        }

        if (friend == null) {
            log.warn("User with this id = {} does not exist", friendId);
            throw new UserNotFoundException("User with this id " + friendId + " does not exist");
        }

        user.deleteFriend(friendId);
        friend.deleteFriend(id);
        return true;
    }

    @Override
    public User getUser(long id) {
        User user = users.get(id);
        if (user == null) {
            log.warn("User with this id = {} does not exist", id);
            throw new UserNotFoundException("User with this id " + id + " does not exist");
        }
        return user;
    }

    @Override
    public Collection<User> getUserFriends(long id) {
        User user = users.get(id);
        if (user == null) {
            log.warn("User with this id = {} does not exist", id);
            throw new UserNotFoundException("User with this id " + id + " does not exist");
        }
        LinkedList<User> friends = new LinkedList<>();
        for (Long friendId : user.getFriends()) {
            friends.addLast(users.get(friendId));
        }
        return friends;
    }

    public long getIdForUser() {
        while (users.containsKey(id)) {
            id++;
        }
        return id;
    }

}