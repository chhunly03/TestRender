package com.khrd.testdeploy.service;


import com.khrd.testdeploy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface UserService {
    CompletableFuture<List<User>> getUsers();

    CompletableFuture<Optional<User>> findById(Long id);

    CompletableFuture<User> addUser(User user);

    CompletableFuture<User> updateUser(Long id, User userDetails) throws Exception;

    CompletableFuture<Boolean> deleteUser(Long id);
}
