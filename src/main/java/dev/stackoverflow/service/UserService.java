package dev.stackoverflow.service;

import dev.stackoverflow.exception.UserNotFoundException;
import dev.stackoverflow.model.User;
import dev.stackoverflow.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(@NonNull Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User saveUser(@NonNull User user) {
        return userRepository.save(user);
    }

    public List<User> saveUsers(@NonNull List<User> users) {
        return userRepository.saveAll(users);
    }

    public User updateUser(@NonNull User user, @NonNull Long id) {
        return userRepository.save(user);
    }

    public void deleteUser(@NonNull Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
