package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.exceptions.WrongPasswordException;
import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.repositories.contracts.CredentialRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CredentialRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean usernameExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Credential> query = session.createQuery("FROM Credential WHERE username = :username", Credential.class);
            query.setParameter("username", username);
            List<Credential> result = query.getResultList();
            return !result.isEmpty();
        }
    }

    @Override
    @Transactional
    public void createCredential(Credential credential) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(credential);
            session.getTransaction().commit();
        }
    }

    @Override
    public Credential getUserCredentials(String username, String password) {
        Optional<Credential> credential = getCredentialByUsername(username);
        credential.orElseThrow(() -> new EntityNotFoundException("User", "username", username));
//        Credential credential = getCredentialByUsername(username);
//        if (credential == null) {
//            throw new EntityNotFoundException("User", "username", username);
//        }
        if (!credential.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return credential.get();
    }

    @Override
    public Optional<Credential> getCredentialByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Credential> query = session.createQuery("FROM Credential WHERE username = :username", Credential.class);
            query.setParameter("username", username);
            List<Credential> result = query.getResultList();
            return Optional.of(result.get(0));
        }
    }
}
