package dev.stackoverflow.controller;

import dev.stackoverflow.model.User;
import dev.stackoverflow.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController @RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/users")
    @ResponseBody
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/users/{user-id}")
    @ResponseBody
    public User getUserById(@PathVariable("user-id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/users/{user-id}")
    @ResponseBody
    public User updateUser(@NonNull @RequestBody User user, @NonNull @PathVariable("user-id") Long userId) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/users/{user-id}")
    public void deleteUserById(@NonNull @PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
    }

}
