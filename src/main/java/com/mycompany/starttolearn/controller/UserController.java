package com.mycompany.starttolearn.controller;


import com.mycompany.starttolearn.model.Pet;
import com.mycompany.starttolearn.service.PetService;
import com.mycompany.starttolearn.service.UserService;
import com.mycompany.starttolearn.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

//    @GetMapping("/static")
//    public String getHTML() {
//        return "this is api page, hello there";
//    }
//
//    @GetMapping("/myJaba")
//    public String helloJaba() {
//        return "hello my little jaba yulinka! \n this is you \n ";
//    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAllUsers();

        if (users == null || users.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@Min(value = 1) @PathVariable long id) {
        Optional<User> optionalUser = userService.findUserById(id);

        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(optionalUser.get());


    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());
        for (Pet pet : user.getPets()) {
            pet.setUser(user); // Устанавливаем связь
        }

        userService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/batch")
    public ResponseEntity<?> saveAllUsers(@Valid @RequestBody List<User> users, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());
        for (User user : users) {
            // Устанавливаем связь между пользователем и его питомцами
            for (Pet pet : user.getPets()) {
                pet.setUser(user);
            }
        }

        userService.saveAllUsers(users);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Min(value = 1) @PathVariable long id) {
        Optional<User> optionalUser = userService.findUserById(id);
        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();

        userService.deleteUserById(id);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@Valid @PathVariable long id, @RequestBody User user, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());

        userService.editUser(id, user);
        return ResponseEntity.ok().build();

    }



}
