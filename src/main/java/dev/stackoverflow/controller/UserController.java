package dev.stackoverflow.controller;

import dev.stackoverflow.model.User;
import dev.stackoverflow.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/users/all")
    @ResponseBody
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/users")
    @ResponseBody
    public User getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/users")
    @ResponseBody
    public User updateUser(@NonNull @RequestParam("user_id") Long userId, @NonNull @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @PutMapping("/users/score")
    @ResponseBody
    public User updateUserScore(@NonNull @RequestParam("user_id") Long userId, @NonNull @RequestBody Double amount) {
        return userService.updateUserScore(userId, amount);
    }

    @DeleteMapping("/users")
    public void deleteUserById(@NonNull @RequestParam("user_id") Long userId) {
        userService.deleteUser(userId);
    }

}

