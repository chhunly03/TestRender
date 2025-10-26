package com.khrd.testdeploy.controller;

import com.khrd.testdeploy.model.User;
import com.khrd.testdeploy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ GET all users
    @GetMapping
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        return userService.getUsers().thenApply(ResponseEntity::ok);
    }

    // ✅ GET user by ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return userService.findById(id).thenApply(optionalUser -> optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    // ✅ CREATE new user
    @PostMapping
    public CompletableFuture<ResponseEntity<User>> addUser(@RequestBody User user) {
        return userService.addUser(user).thenApply(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

    // ✅ UPDATE user by ID
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User userDetails) throws Exception {
        return userService.updateUser(id, userDetails).thenApply(ResponseEntity::ok).exceptionally(ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ DELETE user by ID — return message + proper HTTP status
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id).thenApply(deleted -> {
            if (deleted) {
                return ResponseEntity.ok(Map.of("status", 200, "message", "User deleted successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", 404, "message", "User not found."));
            }
        });
    }

    // Example static endpoint
    @GetMapping("/fruit")
    public List<String> getList() {
        return List.of("Apple", "Banana", "Orange", "Mango");
    }
}
