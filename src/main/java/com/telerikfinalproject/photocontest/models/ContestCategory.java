package com.telerikfinalproject.photocontest.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contests_categories")
public class ContestCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Category cannot be null")
    @Size(min = 2, max = 20, message = "Category should be between 2 and 50 symbols")
    @Column(name = "category")
    private String category;

    public ContestCategory() {
    }

    public ContestCategory(String category) {
        this.category = category;
    }

    public ContestCategory(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
