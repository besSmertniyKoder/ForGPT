package com.mycompany.starttolearn.repository;

import com.mycompany.starttolearn.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUserId(long userId);

    Optional<Pet> findByUserIdAndId(long userId, long id);
}
