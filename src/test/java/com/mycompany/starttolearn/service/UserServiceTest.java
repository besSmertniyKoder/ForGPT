package com.mycompany.starttolearn.service;

import com.mycompany.starttolearn.model.User;
import com.mycompany.starttolearn.repository.PetRepository;
import com.mycompany.starttolearn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petRepository;
    @InjectMocks
    UserService userService;
    @Test
    public void testGetUserById(){
        User user = new User(1l,"john","doe",null);
        //настройка поведения
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserById(1l);
        assertTrue(result.isPresent());
        assertEquals("john",result.get().getFirstName());
        assertEquals("doe",result.get().getLastName());
        verify(userRepository, times(1)).findById(1l);

    }
    @Test
    public void testSaveUser(){
        User user = new User();
        user.setFirstName("john");
        user.setFirstName("doe");
        userService.saveUser(user);
        verify(userRepository,times(1)).save(user);
    }
    @Test
    public void testDeleteUser(){
        userService.deleteUserById(1L);
        verify(userRepository,times(1)).deleteById(1l);
    }
    @Test
    public void testGetUserByIdNotFound() {
        // Настройка поведения mock-объекта userRepository, чтобы при вызове findById возвращался пустой Optional
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Вызов метода getUserById у userService
        Optional<User> result = userService.findUserById(1l);

        // Проверка, что результат пустой (Optional не содержит значения)
        assertFalse(result.isPresent());
    }

}
