package org.example.rest_apis.controller;


import org.example.rest_apis.model.User;
import org.example.rest_apis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        return "User ID Is:" + id;
        return userService.getUserById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        User createdUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user) {
        return userService.updateUser(id,user).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable Long id) {

        Optional<User> deletedUser = userService.deleteUser(id);

        if (deletedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = Map.of(
                "message", "User ID " + id + " deleted successfully",
                "user", deletedUser.get()
        );

        return ResponseEntity.ok(response);
    }
}
