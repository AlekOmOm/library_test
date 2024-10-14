package com.alek0m0m.librarytest_adventurexpbackend.model;

import com.Alek0m0m.library.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties({"activity"})
public class EquipmentType extends BaseEntity<Long> {

    // Constructors, getters, and setters
    public EquipmentType() {
    }

    public EquipmentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EquipmentType{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}