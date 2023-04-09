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

    @GetMapping("/users/all")
    @ResponseBody
    public List<User> getAll() {
        return userService.getUsers();
    }

//    @GetMapping("/users")
//    @ResponseBody
//    public User getUserById(@RequestParam("user-id") Long userId) {
//        return userService.getUserById(userId);
//    }

    @GetMapping("/users")
    @ResponseBody
    public User getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/users")
    @ResponseBody
    public User updateUser(@NonNull @RequestBody User user, @NonNull @RequestParam("user-id") Long userId) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/users")
    public void deleteUserById(@NonNull @RequestParam("user-id") Long userId) {
        userService.deleteUser(userId);
    }

}
