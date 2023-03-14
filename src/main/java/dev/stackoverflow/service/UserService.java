package dev.stackoverflow.service;

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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(@NonNull Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void saveUsers(@NonNull List<User> users) {
        userRepository.saveAll(users);
    }

    public User updateUser(@NonNull User user, @NonNull Long id) {
        if (userRepository.existsById(id)) {
            User newUser = new User(
                    id,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhoneNumber(),
                    user.getScore(),
                    user.isBanned(),
                    user.isModerator()
            );
            return userRepository.save(newUser);
        } else {
            return userRepository.save(user);
        }
    }

    public void deleteUserById(@NonNull Long id) {
        userRepository.deleteById(id);
    }
}
