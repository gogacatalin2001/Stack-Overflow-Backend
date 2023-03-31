package dev.stackoverflow.service;

import dev.stackoverflow.exception.UserNotFoundException;
import dev.stackoverflow.model.User;
import dev.stackoverflow.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(@NonNull Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByUsername(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(@NonNull User user) {
        return userRepository.save(user);
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
