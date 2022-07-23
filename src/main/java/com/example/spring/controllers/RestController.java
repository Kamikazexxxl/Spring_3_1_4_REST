package com.example.spring.controllers;


import com.example.spring.models.Role;
import com.example.spring.models.User;
import com.example.spring.services.RoleService;
import com.example.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/jj")
    public void get(@RequestBody User user){
        System.out.println(user);
        userService.save(user);
    }

    @GetMapping("/current")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.findUserByUserName(user.getName());
    }

    @GetMapping("/users")
    public List<User> getInfo() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.save(user);

    }

    @GetMapping("/allroles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PatchMapping("/users")
    public String editUser(@RequestBody User user) {
        System.out.println(user);
        userService.edit(user);
        return "User was altered";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.remove(id);
        return  "User was deleted";
    }
}
