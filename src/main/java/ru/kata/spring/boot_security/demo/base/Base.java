package ru.kata.spring.boot_security.demo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Base {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Base(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @PostConstruct
    public void baseUsers() {
        roleService.addRole(new Role("ADMIN"));
        roleService.addRole(new Role("USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> allRoles = new HashSet<>();

        adminRole.add(roleService.getRoleById(1L));
        userRole.add(roleService.getRoleById(2L));
        allRoles.add(roleService.getRoleById(1L));
        allRoles.add(roleService.getRoleById(2L));

        userService.addUser(new User("Martin", "Scorsese",  "scorseseMartin@gmail.com",
                "user", "admin", userRole));
        userService.addUser(new User("Stanley ", "Kubrick",  "Stan1928@rambler.ru",
                "admin", "user", adminRole));
        userService.addUser(new User(" Sam", "Mendes",  "menDes007@mail.ru",
                "good", "bubby", userRole));
        userService.addUser(new User("Christopher", "Nolan",  "oscar@gmail.com",
                "TYhk12", "liberty", allRoles));
        userService.addUser(new User("Quentin", "Tarantino",  "western@yandex.ru",
                "candy", "halcyon", userRole));
        userService.addUser(new User("Joel", "Coen",  "fargoTheBest@mail.ru",
                "123456", "hiHello", allRoles));
        userService.addUser(new User("Martin", "McDonagh",  "martin53@gmail.com",
                "england", "damask", adminRole));
    }
}