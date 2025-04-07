package com.example.petshopnew.controller;



import com.example.petshopnew.entity.Review;
import com.example.petshopnew.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/product/{productId}")
    public List<Review> getReviewsForProduct(@PathVariable Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }


    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id) {
        Review review = reviewService.getReview(id);
        if (review != null) {
            return ResponseEntity.ok().body(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestParam Long userId, @RequestParam Long productId,
                                            @RequestParam int rating, @RequestParam String comment) {
        Review createdReview = reviewService.addReview(userId, productId, rating, comment);
        return ResponseEntity.ok().body(createdReview);
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestParam int rating,
                                               @RequestParam String comment) {
        Review updatedReview = reviewService.updateReview(id, rating, comment);
        return ResponseEntity.ok().body(updatedReview);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
