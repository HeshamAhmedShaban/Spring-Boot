package org.example.rest_apis.service;

import org.example.rest_apis.model.Address;
import org.example.rest_apis.model.User;
import org.example.rest_apis.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> updateUser(Long id, User updatedUser) {

        // 1. Find existing user
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            return Optional.empty();
        }

        // 2. Get existing user
        User existingUser = existingUserOptional.get();

        // 3. Update basic information
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        // 4. Update addresses
        List<Address> updatedAddresses = updatedUser.getAddress();

        if (updatedAddresses != null) {

            // Remove old addresses that are not included in the request
            existingUser.getAddress().removeIf(existingAddress ->
                    updatedAddresses.stream()
                            .noneMatch(updatedAddress ->
                                    updatedAddress.getId() != null &&
                                            updatedAddress.getId().equals(existingAddress.getId())
                            )
            );

            // Add new addresses or update existing ones
            for (Address updatedAddress : updatedAddresses) {

                if (updatedAddress.getId() == null) {

                    // New address
                    updatedAddress.setUser(existingUser);
                    existingUser.addAddress(updatedAddress);

                } else {

                    // Existing address
                    existingUser.getAddress().stream()
                            .filter(address ->
                                    address.getId().equals(updatedAddress.getId())
                            )
                            .findFirst()
                            .ifPresent(address -> {
                                address.setStreet(updatedAddress.getStreet());
                                address.setCity(updatedAddress.getCity());
                                address.setCountry(updatedAddress.getCountry());
                                address.setPostalCode(updatedAddress.getPostalCode());
                            });
                }
            }
        }

        // 5. Return updated user
        return Optional.of(userRepository.save(existingUser));
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {

        user.setId(null);

        if (user.getAddress() != null) {
            for (Address address : user.getAddress()) {
                address.setId(null);
                address.setUser(user);
            }
        }

        return userRepository.save(user);
    }

    public Optional<User> deleteUser(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        userRepository.delete(user.get());

        return user;
    }
}
