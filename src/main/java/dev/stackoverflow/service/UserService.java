package dev.stackoverflow.service;

import dev.stackoverflow.model.User;
import dev.stackoverflow.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(@NonNull Long id) {
        // TODO implement
        return null;
    }

    public void saveUser(@NonNull User user) {
        userRepository.save(user);
    }

//    public void updateUser(User user, Long id) {
//        userRepository.deleteById(id);
//        userRepository.save(user);
//    }

    public void deleteUser(@NonNull Long id) {
        userRepository.deleteById(id);
    }
}
