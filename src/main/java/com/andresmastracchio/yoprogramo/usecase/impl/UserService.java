package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.UserRepository;
import com.andresmastracchio.yoprogramo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getByusername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean existsByName(String username){
        return userRepository.existsByUsername(username);
    }

    public  boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
