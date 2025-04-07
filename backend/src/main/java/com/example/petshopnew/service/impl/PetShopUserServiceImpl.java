package com.example.petshopnew.service.impl;

import com.example.petshopnew.entity.PetShopUser;

import com.example.petshopnew.repository.PetShopUserRepository;
import com.example.petshopnew.service.CartService;
import com.example.petshopnew.service.PetShopUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetShopUserServiceImpl implements PetShopUserService, UserDetailsService {

    private final PetShopUserRepository petShopUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public PetShopUserServiceImpl(PetShopUserRepository petShopUserRepository,
                                  PasswordEncoder passwordEncoder,
                                  CartService cartService) {
        this.petShopUserRepository = petShopUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @Override
    public List<PetShopUser> getAllUsers() {
        return petShopUserRepository.findAll();
    }

    @Override
    public PetShopUser getUserById(Long id) {
        return petShopUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public PetShopUser updateUser(Long id, PetShopUser user) {
        PetShopUser existingUser = petShopUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрирање на лозинката
        existingUser.setRole(user.getRole());

        return petShopUserRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        PetShopUser user = petShopUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        petShopUserRepository.delete(user);
    }

    @Override
    public PetShopUser getUserByEmail(String email) {
        return petShopUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }

    @Override
    public PetShopUser createUser(PetShopUser user) {
        if (petShopUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

       user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return petShopUserRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PetShopUser user = petShopUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getFirstName())
                .password(user.getPassword())
                .roles(user.getRole().name()) // Сетирање на ролите
                .build();
    }
}
