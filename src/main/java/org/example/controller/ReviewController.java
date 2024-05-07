package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Review;
import org.example.entity.User;
import org.example.service.AuthService;
import org.example.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {

        Review review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());
        Review createdReview = reviewService.createReview(review, user);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());
        Review updatedReview = reviewService.updateReview(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}

