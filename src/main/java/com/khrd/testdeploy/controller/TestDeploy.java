package com.khrd.testdeploy.controller;

import com.khrd.testdeploy.model.User;
import com.khrd.testdeploy.repositpory.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class TestDeploy {

    private final UserRepository userRepository;

    public TestDeploy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new user
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // UPDATE user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        user.setName(userDetails.getName());
        user.setPassword(userDetails.getPassword());
        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Example static endpoint
    @GetMapping("/fruit")
    public List<String> getList() {
        return List.of("Apple", "Banana", "Orange", "Mango");
    }
}
