package com.alek0m0m.librarytest_adventurexpbackend.service;

import com.Alek0m0m.library.spring.web.mvc.BaseService;
import com.alek0m0m.librarytest_adventurexpbackend.model.*;
import com.alek0m0m.librarytest_adventurexpbackend.service.*;
import com.alek0m0m.librarytest_adventurexpbackend.repository.*;
import com.alek0m0m.librarytest_adventurexpbackend.config.SequenceResetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService extends BaseService<Activity, Long> {

    private final SequenceResetter sequenceResetter;
    private final BookingRepository bookingRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, BookingRepository bookingRepository,  SequenceResetter sequenceResetter) {
        super(activityRepository);
        this.sequenceResetter = sequenceResetter;
        this.bookingRepository = bookingRepository;
    }

    // ------------------- Operations -------------------

    // ------------------- 1. Create -------------------


    @Transactional
    public List<Activity> saveAllActivities(List<Activity> activities) {
        System.out.println("Debug: ActivityService: saveAllActivities");
        List<Activity> savedActivities = new ArrayList<>();

        List<Activity> repoList = findAll();
        if (!repoList.isEmpty()) {
            sequenceResetter.resetAutoIncrement("activity", repoList.getLast().getId() + 1);
        }

        for (Activity activity : activities) {
            savedActivities.add(save(activity)); // Transactional
        }

        System.out.println();
        System.out.println(" activities amount: "+ activities.size());
        System.out.println(" savedActivities amount: "+ savedActivities.size());
        System.out.println(" activities non-functional equipment amount: "+ getAllNonfunctional(activities.getFirst().getEquipmentList()).size());
        System.out.println(" savedActivities non-functional equipment amount: "+ getAllNonfunctional(savedActivities.getFirst().getEquipmentList()).size());

        return savedActivities;
    }


    private List<Equipment> getAllNonfunctional(List<Equipment> list) {

        List<Equipment> nonFunctional = new ArrayList<>();
        for (Equipment equipment : list) {
            if (!equipment.isFunctional()) {
                nonFunctional.add(equipment);
            }
        }
        return nonFunctional;

    }

    // ------------------- 2. Read -------------------

    public Activity getActivity(Activity activity) {
        return find(activity);
    }

    // ------------------- 3. Update -------------------

    @Transactional
    public Activity updateActivity(Activity activity) {
        Optional<Activity> existingActivityOpt = Optional.ofNullable(getActivity(activity));
        System.out.println();
        System.out.println("Debug: ActivityService: updateActivity");
        System.out.println(" Activity: " + activity);
        System.out.println(" ExistingActivity: " + existingActivityOpt);
        System.out.println();

        if (existingActivityOpt.isPresent()) {
            Activity existingActivity = updateActivityFromExistent(activity, existingActivityOpt);

            if (activity.getEquipmentList() != null) {
                existingActivity.getEquipmentList().clear();
            }
            existingActivity.getEquipmentList().addAll(activity.getEquipmentList());
            if (activity.getEquipmentTypes() != null) {
                existingActivity.getEquipmentTypes().clear();
            }
            existingActivity.getEquipmentTypes().addAll(activity.getEquipmentTypes());

            return save(existingActivity);
        } else {
            throw new IllegalArgumentException("Activity not found");
        }
    }

    private static Activity updateActivityFromExistent(Activity activity, Optional<Activity> existingActivityOpt) {

        System.out.println("Debug: ActivityService: updateActivityFromExistent");
        System.out.println(" Activity: " + activity);
        System.out.println("  equipmentList(): " + activity.getEquipmentList().size());
        System.out.println(" ExistingActivity: " + existingActivityOpt);
        System.out.println("  equipmentList(): " + existingActivityOpt.get().getEquipmentList().size());

        if (existingActivityOpt.isEmpty()) {
            throw new IllegalArgumentException("Activity not found");
        }
        Activity existingActivity = existingActivityOpt.get();
        existingActivity.updateFrom(activity);
        return existingActivity;
    }


    @Transactional
    public void updateEquipmentForActivity(Long activityId, List<Equipment> newEquipmentList) {
        Activity existingActivity = findById(activityId);
        existingActivity.setEquipmentList(newEquipmentList);
        save(existingActivity);
    }



    // ------------------- 4. Delete -------------------

    @Transactional
    public void delete(Activity activity) {
        List<Booking> bookings = bookingRepository.findByActivity(activity);
        bookingRepository.deleteAll(bookings);
        getRepository().delete(activity);
    }


    // ------------------- 5. Other -------------------

    public Activity bookAndSave(Activity frontendActivity) {
        Activity repoActivity = getActivity(frontendActivity);

        if (frontendActivity.equals(repoActivity)) {
            return null;
        }

        // 2 step - book the timeslots
        List<TimeSlot> timeSlots = frontendActivity.getTimeSlots();
        List<TimeSlot> repoTimeSlots = repoActivity.getTimeSlots();

        for (TimeSlot timeSlot : timeSlots) {
            for (TimeSlot repoTimeSlot : repoTimeSlots) {
                if (timeSlot.equals(repoTimeSlot)) {
                    repoTimeSlot.setCurrentParticipants(timeSlot.getCurrentParticipants());
                }
            }
        }

        repoActivity.setTimeSlots(repoTimeSlots);

        // 3 step - save
        return save(repoActivity);
    }


    // ------------------- 5. Other -------------------

    public void multiplyEquipmentTypes(Activity activity) {
        List<Equipment> multipliedEquipmentList = new ArrayList<>();
        for (EquipmentType equipmentType : activity.getEquipmentTypes()) {
            for (int i = 0; i < activity.getPersonsMax(); i++) {
                Equipment equipment = new Equipment(equipmentType.getName(), true, false);
                multipliedEquipmentList.add(equipment);
            }
        }
        activity.setEquipmentList(multipliedEquipmentList);
    }


    // ------------------- HTTP Helper Methods -------------------

    public ResponseEntity<Activity> checkActivity(Activity activity) {
        return Optional.ofNullable(activity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void validateOptional(Optional<?> optional, String entityName) {
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " not found");
        }
    }

    public <T> ResponseEntity<List<T>> createResponseEntity(List<T> data) {
        return data.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static void setObjects(Long id, Activity activity, Activity existingActivity) {
        activity.setId(id);
        activity.setEquipmentList(existingActivity.getEquipmentList());
        activity.setEquipmentTypes(existingActivity.getEquipmentTypes());
        activity.setTimeSlots(existingActivity.getTimeSlots());
    }



}


