package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createOrUpdateUser(User user);

    User getUser(long id);

    void deleteUser(User user);

}
