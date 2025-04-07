package com.example.petshopnew.repository;



import com.example.petshopnew.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    public List<Review> findByPetShopUserId(Long userId);
}