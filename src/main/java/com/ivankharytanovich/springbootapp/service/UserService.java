package com.ivankharytanovich.springbootapp.service;

import com.ivankharytanovich.springbootapp.model.User;
import com.ivankharytanovich.springbootapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public Long getIdByUsername(String username) {
        return this.getUserByUsername(username).getId();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean isUsernameTaken(String username) {
        return null != this.loadUserByUsername(username);
    }

    @Transactional
    public void updateUserById(Long id, User user) {
        userRepository.queryUserById(id, user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getStatus(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }
}
