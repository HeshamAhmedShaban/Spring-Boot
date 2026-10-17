package org.example.rest_apis.repository;

import org.example.rest_apis.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    // Get all users
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    // Get user by ID
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    // Save a new user or update an existing one
    public User saveNewUser(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        }

        users.put(user.getId(), user);

        return user;
    }

    // Delete user by ID
    public Optional<User> deleteById(Long id) {
        return Optional.ofNullable(users.remove(id));
    }
}
