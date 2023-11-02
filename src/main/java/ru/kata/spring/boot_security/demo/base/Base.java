package ru.kata.spring.boot_security.demo.base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

        List<Long> adminRoleIds = new ArrayList<>();
        List<Long> userRoleIds = new ArrayList<>();
        List<Long> allRoleIds = new ArrayList<>();

        adminRoleIds.add(1L);
        userRoleIds.add(2L);
        allRoleIds.add(1L);
        allRoleIds.add(2L);

        userService.addUserWithRoles(new User("Scorsese", 80,  "scorseseMartin@gmail.com",
                "user", "admin"), adminRoleIds);
        userService.addUserWithRoles(new User("Kubrick", 70, "Stan1928@rambler.ru","admin",
                "user"), adminRoleIds);
        userService.addUserWithRoles(new User( "Mendes", 58, "menDes007@mail.ru",
                "good", "bubby"), userRoleIds);
        userService.addUserWithRoles(new User( "Nolan", 53, "oscar@gmail.com",
                "TYhk12", "liberty"), allRoleIds);
        userService.addUserWithRoles(new User( "Tarantino",  60,"western@yandex.ru",
                "candy", "halcyon"), userRoleIds);
        userService.addUserWithRoles(new User( "Coen",  68,"fargoTheBest@mail.ru",
                "123456", "hiHello"), allRoleIds);
        userService.addUserWithRoles(new User( "McDonagh", 53, "martin53@gmail.com",
                "england", "damask"), adminRoleIds);
    }
}
