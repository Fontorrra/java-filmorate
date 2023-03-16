package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    boolean deleteUser(long id);

    boolean updateUser(User user);

    Collection<User> getUsers();

    boolean addFriend(long id, long friendId);

    User getUser(long id);

    boolean deleteFriend(long id, long friendId);

    Collection<User> getUserFriends(long id);
}
