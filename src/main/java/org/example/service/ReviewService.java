package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.ReviewDao;
import org.example.entity.Review;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewDao reviewDao;

    public Review getReviewById(Long reviewId) {
        return reviewDao.findById(reviewId);
    }
    @Transactional
    public Review createReview(Review review, User user) {
        review.setUser(user);
        return reviewDao.save(review);
    }

    @Transactional
    public Review updateReview(Long reviewId, Review review) {
        Review existingReview = reviewDao.findById(reviewId);
        if (existingReview == null) {
            throw new IllegalArgumentException("Review not found with id " + reviewId);
        }
        review.setId(reviewId);
        return reviewDao.save(review);
    }
    @Transactional
    public void deleteReview(Long reviewId) {
        Review existingReview = reviewDao.findById(reviewId);
        if (existingReview == null) {
            throw new IllegalArgumentException("Review not found with id " + reviewId);
        }
        reviewDao.delete(existingReview);
    }
}
