package org.example.rest_apis.service;

import org.example.rest_apis.model.Address;
import org.example.rest_apis.model.User;
import org.example.rest_apis.repository.AddressRepository;
import org.example.rest_apis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    public UserService(UserRepository userRepository,AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // The server generates the ID for a new user
        user.setId(null);
        List<Address> addresses = user.getAddress();

        if (addresses != null) {
            for (Address address : addresses) {
                address.setId(null);
                addressRepository.save(address);
            }
        }
        return userRepository.saveNewUser(user);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            return Optional.empty();
        }

        updatedUser.setId(id);

        return Optional.of(userRepository.saveNewUser(updatedUser));
    }

    public Optional<User> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
