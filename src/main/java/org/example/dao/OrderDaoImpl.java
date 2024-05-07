package org.example.dao;

import org.example.entity.Order;
import org.example.entity.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> findByUser(User user) {
        String jpql = "SELECT o FROM Order o WHERE o.user = :user";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
