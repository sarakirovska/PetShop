package com.example.petshopnew.service.impl;




import com.example.petshopnew.entity.PetShopUser;
import com.example.petshopnew.entity.Product;
import com.example.petshopnew.entity.Review;
import com.example.petshopnew.repository.PetShopUserRepository;
import com.example.petshopnew.repository.ProductRepository;
import com.example.petshopnew.repository.ReviewRepository;
import com.example.petshopnew.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PetShopUserRepository petShopUserRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             PetShopUserRepository petShopUserRepository,
                             ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.petShopUserRepository = petShopUserRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Review addReview(Long userId, Long productId, int rating, String comment) {
        PetShopUser user = petShopUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));


        Review review = new Review();
        review.setPetShopUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());


        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, int rating, String comment) {

        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));


        existingReview.setRating(rating);
        existingReview.setComment(comment);
        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByPetShopUserId(userId);
    }

}
