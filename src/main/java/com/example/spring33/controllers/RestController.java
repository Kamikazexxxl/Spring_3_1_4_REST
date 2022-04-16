package com.example.spring33.controllers;


import com.example.spring33.models.Role;
import com.example.spring33.models.User;
import com.example.spring33.services.RoleService;
import com.example.spring33.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/current")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.findUserByUserName(user.getName());
    }

    @GetMapping("/users")
    public List<User> getInfo(Model model, Principal principal) {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return user;
    }

    @GetMapping("/allroles")
    public List<Role> getRoles() {

        return roleService.findAll();
    }

    @PatchMapping("/users")
    public String editUser(@ModelAttribute("user") User user) {
        userService.edit(user);
        return "User was altered";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.remove(id);
        return  "User was deleted";
    }
}
