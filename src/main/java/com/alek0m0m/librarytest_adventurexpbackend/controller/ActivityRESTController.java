package com.alek0m0m.librarytest_adventurexpbackend.controller;

import com.Alek0m0m.library.spring.web.mvc.BaseRESTController;
import com.Alek0m0m.library.spring.web.mvc.BaseServiceInterface;
import com.alek0m0m.librarytest_adventurexpbackend.model.*;
import com.alek0m0m.librarytest_adventurexpbackend.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alek0m0m.librarytest_adventurexpbackend.service.ActivityService.setObjects;


@RestController
@RequestMapping("/Activity")
@CrossOrigin(origins = "*")
public class ActivityRESTController extends BaseRESTController<Activity, Long> {

    // endpoints from BaseRESTController in use:
            // getAll, getById, delete

    private final ActivityService activityService;

    @Autowired
    public ActivityRESTController(ActivityService service) {
        super(service);
        activityService = service;
    }



    // ------------------- Operations -------------------

    // ------------------- 1. Create -------------------
    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity ) {
        System.out.println("createActivity");
        System.out.println(" Activity: " + activity);

        activityService.multiplyEquipmentTypes(activity);

        return ResponseEntity.ok(getService().save(activity));
    }

    // ------------------- 2. Read -------------------
        // getAll is already implemented with extension BaseRESTController

    @GetMapping("/types")
    public ResponseEntity<List<Activity>> getAllActivityTypes() {
        return ResponseEntity.ok(getService().findAll());
    }

        // getById is already implemented with extension BaseRESTController


    @GetMapping("/{name}")
    public ResponseEntity<Activity> getActivityByName(@PathVariable String name) {
        return activityService.checkActivity(getService().findByName(name));
    }

    // ------------------- 3. Update -------------------
    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {

        setObjects(id, activity, getService().findById(id));

        return activityService.checkActivity(getService().save(activity));
    }



    @PutMapping("/{id}/equipment")
    public ResponseEntity<Void> updateEquipmentList(@PathVariable Long id, @RequestBody List<Equipment> newEquipmentList) {
        System.out.println("Updating equipment list for activity with ID: " + id);
        try {
            activityService.updateEquipmentForActivity(id, newEquipmentList);
            System.out.println("Equipment list updated for activity with ID: " + id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            System.out.println("Activity with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------- 4. Delete -------------------

        // delete is already implemented with extension BaseRESTController






}
