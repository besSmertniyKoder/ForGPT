package com.mycompany.starttolearn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long id;
    @Column(name = "pet_name")
    @NotBlank(message = "pet name is null/empty!")
    private String name;
    @Column(name = "pet_type")
    @NotBlank(message = "pet type is null/empty!")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Pet() {
    }

    public Pet(long id, String name, String type, User user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", user=" + user.showUserInfoForPets() +
                '}';
    }
}
