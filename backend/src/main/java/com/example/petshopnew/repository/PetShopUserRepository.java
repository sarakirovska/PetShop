package com.example.petshopnew.repository;



import com.example.petshopnew.entity.PetShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetShopUserRepository extends JpaRepository<PetShopUser, Long> {
    Optional<PetShopUser> findByEmail(String email);
    Optional<PetShopUser> findByEmailAndPassword(String email, String password);
}