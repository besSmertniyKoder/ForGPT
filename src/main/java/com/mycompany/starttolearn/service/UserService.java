package com.mycompany.starttolearn.service;

import com.mycompany.starttolearn.model.Pet;
import com.mycompany.starttolearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.starttolearn.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        if (user != null) userRepository.save(user);

    }

    public void saveAllUsers(List<User> users) {
        if (users != null) userRepository.saveAll(users);

    }

    public Optional<User> findUserById(long id) {

        return userRepository.findById(id);


    }

    public void deleteUserById(long id) {

        userRepository.deleteById(id);
    }

    public void editUser(long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            userRepository.save(existingUser);
        }

    }



}
