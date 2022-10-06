package com.khalil.sms_app.services;

import com.khalil.sms_app.models.Division;
import org.springframework.stereotype.Service;

import com.khalil.sms_app.models.User;
import com.khalil.sms_app.repositories.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User userRegister(User user) {
        return userRepository.save(user);
    }
    public User getUserById(Integer user_id) {
        return userRepository.findById(user_id).get();
    }

    public User getUserByUserName(String name) {
        return userRepository.findByUserName(name).get();
    }

    public List<User> findAllUserByDivision(List<Division> division) {
        return userRepository.findAllUserByEmployeeDivision(division);
    }
}
