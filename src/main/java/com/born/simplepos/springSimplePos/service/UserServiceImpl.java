package com.born.simplepos.springSimplePos.service;

import com.born.simplepos.springSimplePos.entity.User;
import com.born.simplepos.springSimplePos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * ✅ Safely updates editable profile fields only.
     * Email and password are NOT changed here.
     */
    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);

        // Editable fields
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setPhone(user.getPhone());
        existing.setAddress(user.getAddress());
        existing.setDateOfBirth(user.getDateOfBirth());

        // Optional: allow username or email updates only if you want
        // existing.setUsername(user.getUsername());
        // existing.setEmail(user.getEmail());

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
