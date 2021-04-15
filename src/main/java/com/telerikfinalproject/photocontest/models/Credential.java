package com.telerikfinalproject.photocontest.models;

import com.telerikfinalproject.photocontest.models.utils.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;

    public Credential() {
    }

    public Credential(int id, String username, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public Credential(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Credential credential = (Credential) o;
//        return id == credential.id && username.equals(credential.username) && password.equals(credential.password);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(username);
//    }
}
