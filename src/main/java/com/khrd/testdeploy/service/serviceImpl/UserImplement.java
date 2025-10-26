package com.khrd.testdeploy.service.serviceImpl;

import com.khrd.testdeploy.model.User;
import com.khrd.testdeploy.repositpory.UserRepository;
import com.khrd.testdeploy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserImplement implements UserService {
    private final UserRepository userRepository;

    public UserImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<List<User>> getUsers() {
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @Override
    public CompletableFuture<Optional<User>> findById(Long id) {
        return CompletableFuture.completedFuture(userRepository.findById(id));
    }

    @Override
    public CompletableFuture<User> addUser(User user) {
        return CompletableFuture.completedFuture(userRepository.save(user));
    }

    @Override
    public CompletableFuture<User> updateUser(Long id, User userDetails) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        });
    }

    @Override
    public CompletableFuture<Boolean> deleteUser(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        });
    }

}
