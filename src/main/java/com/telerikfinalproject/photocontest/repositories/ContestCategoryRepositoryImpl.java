package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.ContestCategory;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestCategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContestCategoryRepositoryImpl implements ContestCategoryRepository {

    private final SessionFactory sessionFactory;

    public ContestCategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ContestCategory> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            Query<ContestCategory> query = session.createQuery("FROM ContestCategory ", ContestCategory.class);
            return query.getResultList();
        }
    }

    @Override
    public ContestCategory getCategoryById(int id) {
        try (Session session = sessionFactory.openSession()) {
            ContestCategory contestCategory = session.get(ContestCategory.class, id);
            if (contestCategory == null) {
                throw new EntityNotFoundException("Category", id);
            }
            return contestCategory;
        }
    }

    @Override
    public void addCategory(ContestCategory category) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean categoryExist(ContestCategory category) {
        try (Session session = sessionFactory.openSession()) {
            Query<ContestCategory> query = session.createQuery("from ContestCategory where category = :categoryName",ContestCategory.class);
            query.setParameter("categoryName",category.getCategory());
            return !query.getResultList().isEmpty();
        }
    }
}
