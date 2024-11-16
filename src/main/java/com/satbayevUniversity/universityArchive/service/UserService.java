package com.satbayevUniversity.universityArchive.service;

import com.satbayevUniversity.universityArchive.domain.User;
import com.satbayevUniversity.universityArchive.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
