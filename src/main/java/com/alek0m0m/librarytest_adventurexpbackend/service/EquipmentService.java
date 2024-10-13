package com.alek0m0m.librarytest_adventurexpbackend.service;

import org.example.adventurexpbackend.model.Equipment;
import org.example.adventurexpbackend.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, RestTemplate restTemplate) {
        this.equipmentRepository = equipmentRepository;
        this.restTemplate = restTemplate;
    }

    // ------------------- Operations -------------------
    public String markAsFunctional(Long equipmentId) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(equipmentId);

        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            equipment.setFunctional(true);
            equipmentRepository.save(equipment);
            return "Equipment marked as functional.";
        } else {
            return "Error: Equipment with ID " + equipmentId + " not found.";
        }
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }


    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment get(Equipment equipment) {
        if(equipment.getId() != null) {
            return equipmentRepository.findById(equipment.getId()).orElse(null);
        } else {
            return equipmentRepository.findByName(equipment.getName());
        }
    }

    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    // Retrieve equipment by name
    public Optional<Equipment> getEquipmentByName(String name) {
        return Optional.ofNullable(equipmentRepository.findByName(name));
    }

    // Delete equipment by id
    public void deleteEquipmentById(Long id) {
        equipmentRepository.deleteById(id);
    }

}
