package com.example.petshopnew.service;





import com.example.petshopnew.entity.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(Long userId, Long productId, int rating, String comment);
    Review updateReview(Long reviewId, int rating, String comment);
    void deleteReview(Long id);
    Review getReview(Long id);
    List<Review> getReviewsForProduct(Long productId);
    List<Review> getReviewsByUser(Long userId);

}
