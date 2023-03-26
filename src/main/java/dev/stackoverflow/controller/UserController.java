package dev.stackoverflow.controller;

import dev.stackoverflow.model.User;
import dev.stackoverflow.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController @RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/user/get/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/get/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/user/save")
    public void saveUser(@NonNull @RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/user/save-all")
    public void saveUsers(@NonNull @RequestBody List<User> users) {
        userService.saveUsers(users);
    }

    @PutMapping("/user/update/{id}")
    @ResponseBody
    public User updateUser(@NonNull @RequestBody User user, @NonNull @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/user/delete/{id}")
    public void deleteUserById(@NonNull @PathVariable Long id) {
        userService.deleteUser(id);
    }

}
