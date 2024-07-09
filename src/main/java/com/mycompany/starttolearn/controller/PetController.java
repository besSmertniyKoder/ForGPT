package com.mycompany.starttolearn.controller;

import com.mycompany.starttolearn.model.Pet;
import com.mycompany.starttolearn.service.PetService;
import com.mycompany.starttolearn.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Все животные привязаны к хозяину, поэтому @RequestMapping("api/user/pets")
//почти все метода требуют user id -> какие не требуют? ->
@RestController
@RequestMapping("api/pets")
public class PetController {
    @Autowired
    private PetService petService;


    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPetsByUserId(@Min(value = 1) @PathVariable long id) {
        List<Pet> pets = petService.getPetByUserId(id);
        if (pets.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(pets.toString());
    }

    @GetMapping("/user{userId}/pet{id}")
    public ResponseEntity<?> getPetByUserIdAndId(@Min(value = 1) @PathVariable long userId,
                                                 @Min(value = 1) @PathVariable long id) {
        Optional<Pet> pet = petService.getPetByIdAndUserId(userId, id);
        System.out.println(pet.get());
        if (!pet.isPresent()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pet.get());
    }


    @PostMapping("user/{id}")
    public ResponseEntity<?> savePet(@Valid @PathVariable long id, @RequestBody Pet pet, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());

        petService.savePet(id, pet);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("user/pet/{id}")// не требует id пользователя
    public ResponseEntity<?> deletePet(@Min(value = 1) @PathVariable long id) {
        Optional<Pet> optionalPet = petService.getPetById(id);
        if (!optionalPet.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        petService.deletePetById(id);


        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPet(@Valid @Min(value = 1) @PathVariable long id,
                                     @RequestBody Pet pet, BindingResult result) {
        Optional<Pet> optionalPet = petService.getPetById(id);
        if (!optionalPet.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        petService.editPet(id, pet);
        return ResponseEntity.ok().build();
    }


}
