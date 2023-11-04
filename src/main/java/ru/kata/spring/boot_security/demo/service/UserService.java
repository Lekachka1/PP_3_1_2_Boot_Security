package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByLogin(String login);
    User getUserById(long id);
    void add(User user,List<String> roles);
    void updateUser(User user,List<Long> roleId);
    void deleteUser(long id);
    void add(User user);
}