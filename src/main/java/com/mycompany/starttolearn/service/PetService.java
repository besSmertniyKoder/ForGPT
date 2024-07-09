package com.mycompany.starttolearn.service;

import com.mycompany.starttolearn.model.Pet;
import com.mycompany.starttolearn.model.User;
import com.mycompany.starttolearn.repository.PetRepository;
import com.mycompany.starttolearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Pet> getPetByUserId(long userId) {

        return petRepository.findByUserId(userId);

    }

    public Optional<Pet> getPetById(long id) {
        return petRepository.findById(id);

    }

    public void savePet(long userId, Pet pet) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            pet.setUser(user);
            petRepository.save(pet);
        } else throw new RuntimeException("Пользователь с таким id не найден!");

    }

    public void deletePetById(long id) {
        petRepository.deleteById(id);
    }
    public void editPet(long id, Pet pet){
       Pet existingPet = petRepository.findById(id).orElse(null);
        if (existingPet != null) {
            if (pet.getUser()!= null){
                existingPet.setUser(pet.getUser());
            }
            existingPet.setName(pet.getName());

            petRepository.save(existingPet);
        }
    }
    public Optional<Pet> getPetByIdAndUserId(long userId,long id){
        return petRepository.findByUserIdAndId(userId, id);

    }



}
