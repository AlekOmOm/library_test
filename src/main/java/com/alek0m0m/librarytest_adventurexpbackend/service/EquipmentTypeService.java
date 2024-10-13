package com.alek0m0m.librarytest_adventurexpbackend.service;

import org.example.adventurexpbackend.model.EquipmentType;
import org.example.adventurexpbackend.repository.EquipmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public EquipmentTypeService(EquipmentTypeRepository equipmentTypeRepository, RestTemplate restTemplate) {
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.restTemplate = restTemplate;
    }

    // CRUD operations

    public EquipmentType saveEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeRepository.save(equipmentType);
    }

    public EquipmentType getEquipmentType(Long id) {
        return equipmentTypeRepository.findById(id).orElse(null);
    }

    public void deleteEquipmentType(Long id) {
        equipmentTypeRepository.deleteById(id);
    }

    public EquipmentType updateEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeRepository.save(equipmentType);
    }

    public EquipmentType get(EquipmentType equipmentType) {
        if(equipmentType.getId() != null) {
            return equipmentTypeRepository.findById(equipmentType.getId()).orElse(null);
        } else {
            return equipmentTypeRepository.findByName(equipmentType.getName());
        }
    }
}
