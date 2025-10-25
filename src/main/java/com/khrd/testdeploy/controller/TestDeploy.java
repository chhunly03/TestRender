package com.khrd.testdeploy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestDeploy {

    @GetMapping("/fruit")
    public List<String> getList() {
        return List.of("Apple", "Banana", "Orange", "Mango");
    }
}
