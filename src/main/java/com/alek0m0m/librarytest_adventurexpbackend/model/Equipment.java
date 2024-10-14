package com.alek0m0m.librarytest_adventurexpbackend.model;

import com.Alek0m0m.library.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;


@Entity
@JsonIgnoreProperties({"activity"})
public class Equipment extends BaseEntity<Long> {

    private boolean functional;
    private boolean underService;


    // ------------------- Constructors -------------------
    public Equipment() {
    }

    public Equipment(String name, boolean functional, boolean underService) {
        this.name = name;
        this.functional = functional;
        this.underService = underService;
    }

    // ------------------- Getter & Setters -------------------


    public boolean isFunctional() {
        return functional;
    }

    public void setFunctional(boolean functional) {
        this.functional = functional;
    }

    public boolean isUnderService() {
        return underService;
    }

    public void setUnderService(boolean underService) {
        this.underService = underService;
    }


    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", functional=" + functional +
                ", underService=" + underService +
                '}';
    }
}