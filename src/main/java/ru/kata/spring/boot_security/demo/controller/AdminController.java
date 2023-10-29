package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String getAdminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin_page";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found");
        }
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles",roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user,@RequestParam(value = "roles") List<String> selectResult, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            Set<Role> selectedRoles = new HashSet<>();
            for (String roleName : selectResult) {
                selectedRoles.add(roleService.getRole(roleName));
            }
            user.setRoles(selectedRoles);
            User originalUser = userService.getUserById(id);
            originalUser.setFirstName(user.getFirstName());
            originalUser.setLastName(user.getLastName());
            originalUser.setEmail(user.getEmail());
            originalUser.setLogin(user.getLogin());
            originalUser.setPassword(user.getPassword());
            originalUser.setRoles(user.getRoles());
            userService.updateUser(originalUser);
            return "redirect:/admin/";
        }
    }
    @GetMapping("/add")
    public String addUser(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "add_user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam(value = "roles") List<String> selectResult) {
        if (bindingResult.hasErrors()) {
            return "add_user";
        } else {
            Set<Role> selectedRoles = new HashSet<>();
            for (String roleName : selectResult) {
                selectedRoles.add(roleService.getRole(roleName));
            }
            user.setRoles(selectedRoles);
            userService.addUser(user);
            return "redirect:/admin/";
        }
    }

}