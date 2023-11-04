package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    private final RoleService roleService;

    @Autowired

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void add(User user, List<String> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role> userRoles = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                userRoles.add(roleService.getRole(role));
            }
        }
        user.setRoles(userRoles);

        userDao.addUser(user);
    }



    @Override
    @Transactional
    public void add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.addUser(user);
    }


    @Override
    @Transactional
    public void updateUser(User user,List<Long> roleId) {
        if (!user.getPassword().equals(getUserById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        List<Role> roles = roleId.stream().map(roleDao::getRoleById).collect(Collectors.toList());
        user.setRoles(roles);
        userDao.updateUser(user);
    }
    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDao.getUserByLogin(login);
    }
}