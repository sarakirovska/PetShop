package com.example.petshopnew.controller;


import com.example.petshopnew.entity.PetShopUser;

import com.example.petshopnew.service.PetShopUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class PetShopUserController {

    private final PetShopUserService petShopUserService;

    public PetShopUserController(PetShopUserService petShopUserService) {
        this.petShopUserService = petShopUserService;
    }


    @GetMapping
    public List<PetShopUser> getAllUsers() {
        return petShopUserService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<PetShopUser> getUserById(@PathVariable Long id) {
        PetShopUser user = petShopUserService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<PetShopUser> getUserByEmail(@PathVariable String email) {
        PetShopUser user = petShopUserService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<PetShopUser> createUser(@RequestBody PetShopUser user) {
        if (user == null || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            PetShopUser createdUser = petShopUserService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<PetShopUser> updateUser(@PathVariable Long id, @RequestBody PetShopUser user) {
        PetShopUser updatedUser = petShopUserService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(updatedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            petShopUserService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
