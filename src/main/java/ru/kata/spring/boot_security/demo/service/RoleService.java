package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRole(String userRole);

    Role getRoleById(Long id);

    void addRole(Role role);
}
