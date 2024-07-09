package com.mycompany.starttolearn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private long id;

    @Column (name = "first_name")
    @NotBlank(message = "username is null/empty!")
    private String firstName;
    @Column (name = "last_name")
    @NotBlank(message = "users last name is null/empty!")
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pet> pets = new ArrayList<>();

    public User() {
    }

    public User(long id, String firstName, String lastName, List<Pet> pets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pets = pets;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setUser(this);  // Устанавливаем владельца питомца
    }

    // Метод для удаления питомца у пользователя
    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setUser(null);  // Убираем связь с пользователем
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pets=" + pets +
                '}';
    }


    public String showUserInfoForPets() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
