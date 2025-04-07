package com.example.petshopnew.service;





import com.example.petshopnew.entity.PetShopUser;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PetShopUserService extends UserDetailsService {
    List<PetShopUser> getAllUsers();
    PetShopUser getUserById(Long id);
    PetShopUser updateUser(Long id, PetShopUser user);
    void deleteUser(Long id);
    PetShopUser getUserByEmail(String email);
    PetShopUser createUser(PetShopUser user);

}
