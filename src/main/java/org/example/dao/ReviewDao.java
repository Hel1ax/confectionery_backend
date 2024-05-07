package org.example.dao;
import org.example.entity.Review;

public interface ReviewDao {
    Review findById(Long reviewId);
    Review save(Review review);
    void delete(Review review);
}
