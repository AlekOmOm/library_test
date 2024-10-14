package com.alek0m0m.librarytest_adventurexpbackend.controller;

import com.alek0m0m.librarytest_adventurexpbackend.service.*;
import com.alek0m0m.librarytest_adventurexpbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
@CrossOrigin(origins = "*")
public class EquipmentRESTController {

    @Autowired
    private EquipmentService equipmentService;

    // ------------------- 1. Create -------------------
    @PostMapping
    public ResponseEntity<Equipment>createOrUpdateEquipment(@RequestBody Equipment equipment){
        Equipment savedEquipment = equipmentService.save(equipment);
        return ResponseEntity.ok(savedEquipment);
    }

    // ------------------- 2. Read -------------------
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment(){
        List<Equipment> equipment = equipmentService.getAllEquipment();
        System.out.println("Retrieving all equipment");
        System.out.println(" Equipment retrieved: " + equipment.size());
        return ResponseEntity.ok(equipment);
    }

    // Endpoint to retrieve equipment by id
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id){
        Optional<Equipment> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Equipment> getEquipmentByName(@PathVariable String name){
        Optional<Equipment> equipment = equipmentService.getEquipmentByName(name);
        return equipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ------------------- 3. Update -------------------

        // functional
    @PutMapping("/mark-functional/{id}")
    public ResponseEntity<String> markEquipmentAsFunctional(@PathVariable Long id) {
        String result = equipmentService.markAsFunctional(id);

        if (result.contains("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    // ------------------- 4. Delete -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentById(@PathVariable Long id){
        equipmentService.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
    }

    

}
