package dev.stackoverflow.controller;

import dev.stackoverflow.model.User;
import dev.stackoverflow.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseBody
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public void saveUser(@NonNull @RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/save-all")
    public void saveUsers(@NonNull @RequestBody List<User> users) {
        userService.saveUsers(users);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public User updateUser(@NonNull @RequestBody User user, @NonNull @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@NonNull @PathVariable Long id) {
        userService.deleteUser(id);
    }


}
