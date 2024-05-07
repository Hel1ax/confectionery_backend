package org.example.dao;

import org.example.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Review findById(Long reviewId) {
        return entityManager.find(Review.class, reviewId);
    }

    @Override
    public Review save(Review review) {
        if (review.getId() == null) {
            entityManager.persist(review);
            return review;
        } else {
            return entityManager.merge(review);
        }
    }

    @Override
    public void delete(Review review) {
        entityManager.remove(review);
    }
}
