package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import com.telerikfinalproject.photocontest.repositories.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User ", User.class);
            return query.getResultList();
        }
    }

    @Override
    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    @Transactional
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user.getCredential());
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public User updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u where u.credential.username = :username", User.class);
            query.setParameter("username", username);
            List<User> userList = query.getResultList();
            if (userList.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return userList.get(0);
        }
    }

    @Override
    public List<User> getAllJunkies() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u where u.credential.userRole = :userRole", User.class);
            query.setParameter("userRole", UserRole.PHOTO_JUNKIE);
            return query.getResultList();
        }
    }

    @Override
    public List<User> getAllOrganisers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u where u.credential.userRole = :userRole", User.class);
            query.setParameter("userRole", UserRole.ORGANIZER);
            return query.getResultList();
        }
    }

    @Override
    public List<User> getAllJuries() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User " +
                    "where credential.userRole = :userRole and score > 150", User.class);
            query.setParameter("userRole", UserRole.PHOTO_JUNKIE);
            return query.getResultList();
        }
    }

    @Override
    public List<User> getTopJunkies(int amount){
        try(Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery("from User ORDER BY score DESC",User.class);
            query.setMaxResults(amount);
            return query.getResultList();
        }
    }

}
