package com.alek0m0m.librarytest_adventurexpbackend.config;


import com.alek0m0m.librarytest_adventurexpbackend.model.*;
import com.alek0m0m.librarytest_adventurexpbackend.repository.*;
import com.alek0m0m.librarytest_adventurexpbackend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    private final ActivityRepository activityRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final BookingRepository bookingRepository;
    private final SequenceResetter sequenceResetter;
    private final ActivityService activityService;
    private final EquipmentService equipmentService;
    private final EquipmentTypeService equipmentTypeService;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public InitData(ActivityRepository activityRepository, EquipmentRepository equipmentRepository, EquipmentTypeRepository equipmentTypeRepository, BookingRepository bookingRepository, SequenceResetter sequenceResetter, ActivityService activityService, EquipmentService equipmentService, EquipmentTypeService equipmentTypeService, TimeSlotRepository timeSlotRepository) {
        this.activityRepository = activityRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.bookingRepository = bookingRepository;
        this.sequenceResetter = sequenceResetter;
        this.activityService = activityService;
        this.equipmentService = equipmentService;
        this.equipmentTypeService = equipmentTypeService;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearDB();

        sequenceResetter.resetSequences(getStartValues());

        createBookings(createActivities());
    }


    private void clearDB() {
        bookingRepository.deleteAll();
        activityRepository.deleteAll();
        equipmentRepository.deleteAll();
        equipmentTypeRepository.deleteAll();
    }


    private long[] getStartValues() {
        return new long[]{
                activityRepository.findAll().isEmpty() ? 1 : activityRepository.findAll().get(activityRepository.findAll().size()-1).getId() + 1,
                equipmentRepository.findAll().isEmpty() ? 1 : equipmentRepository.findAll().get(equipmentRepository.findAll().size()-1).getId() + 1,
                equipmentTypeRepository.findAll().isEmpty() ? 1 : equipmentTypeRepository.findAll().get(equipmentTypeRepository.findAll().size()-1).getId() + 1,
                timeSlotRepository.findAll().isEmpty() ? 1 : timeSlotRepository.findAll().get(timeSlotRepository.findAll().size()-1).getId() + 1,
                bookingRepository.findAll().isEmpty() ? 1 : bookingRepository.findAll().get(bookingRepository.findAll().size()-1).getId() + 1
        };
    }

    private Set<EquipmentType> createPaintballEquipmentTypes() {
        return new HashSet<>(List.of(
                new EquipmentType("Paintball gun"),
                new EquipmentType("Paintball mask"),
                new EquipmentType("Paintball suit")
        ));
    }

    private Set<EquipmentType> createClimbingEquipmentTypes() {

        return new HashSet<>((new HashSet<>(List.of(
                new EquipmentType("Climbing shoes"),
                new EquipmentType("Climbing harness"),
                new EquipmentType("Climbing chalk")
        ))));
    }

    private Set<EquipmentType> createGoKartEquipmentTypes() {
        return new HashSet<>((new HashSet<>(List.of(
                new EquipmentType("Go-kart car"),
                new EquipmentType("Go-kart helmet"),
                new EquipmentType("Go-kart suit"),
                new EquipmentType("Go-kart gloves")
        ))));
    }

    private List<Equipment> createPaintballEquipmentForOnePerson() {
        return new ArrayList<>(List.of(
                new Equipment("Paintball gun", true, false),
                new Equipment("Paintball mask", true, false),
                new Equipment("Paintball suit", true, false)
        ));
    }

    private List<Equipment> createClimbingEquipmentForOnePerson() {
        return new ArrayList<>(List.of(
                new Equipment("Climbing shoes", true, false),
                new Equipment("Climbing harness", true, false),
                new Equipment("Climbing chalk", true, false)
        ));
    }

    private List<Equipment> createGoKartEquipmentForOnePerson() {
        return new ArrayList<>(List.of(
                new Equipment("Go-kart car", true, false),
                new Equipment("Go-kart helmet", true, false),
                new Equipment("Go-kart suit", true, false),
                new Equipment("Go-kart gloves", true, false)
        ));
    }

    private List<Activity> createActivities() {
        Set<EquipmentType> paintballEquipmentTypes = createPaintballEquipmentTypes();
        Set<EquipmentType> climbingEquipmentTypes = createClimbingEquipmentTypes();
        Set<EquipmentType> goKartEquipmentTypes = createGoKartEquipmentTypes();

        int personsMax = 20;

        System.out.println("Debug createActivities");
        System.out.println(" Paintball non-functional equipmentList: " + getAllNonfunctional(getPaintBallEquipment(personsMax)).size());


        List<Activity> activities = new ArrayList<>(List.of(
                new Activity("Paintball", "Paintball is a fun activity for everyone", 100, 120, 10, 100, 2, personsMax, LocalTime.of(10, 0), LocalTime.of(18, 0), 60, getPaintBallEquipment(personsMax), paintballEquipmentTypes),
                new Activity("Climbing", "Climbing is a fun activity for everyone", 100, 120, 10, 100, 2, personsMax, LocalTime.of(10, 0), LocalTime.of(18, 0), 60, getClimbingEquipmentList(personsMax), climbingEquipmentTypes),
                new Activity("Go-kart", "Go-kart is a fun activity for everyone", 100, 120, 10, 100, 2, personsMax, LocalTime.of(10, 0), LocalTime.of(18, 0), 60, getGoKartEquipmentList(personsMax), goKartEquipmentTypes)
        ));

        System.out.println(" Paintball Activity non-functional equipment: "+ getAllNonfunctional(activities.getFirst().getEquipmentList()).size());
        System.out.println();
        System.out.println("Equipment list size for each activity before and after saving: ");
        for (Activity activity : activities) {
            System.out.println(" " + activity.getName() + ": " + activity.getEquipmentList().size());
        }

        List<Activity> savedActivities = activityService.saveAllActivities(activities);

        for (Activity activity : savedActivities) {
            System.out.println(" " + activity.getName() + ": " + activity.getEquipmentList().size());
        }

        System.out.println();
        System.out.println(" saved paintball activity non-functional equipment: "+ getAllNonfunctional(savedActivities.getFirst().getEquipmentList()).size());
        return savedActivities;
    }

    private List<Equipment> getPaintBallEquipment(int personsMax) {
        List<Equipment> list = new ArrayList<>();
        for(int i = 0; i < personsMax; i++) {
            list.addAll(createPaintballEquipmentForOnePerson());
        }
        // additional two sets of non-functional equipment
        for (int i = 0; i < 2; i++) {
            for (Equipment equipment : createPaintballEquipmentForOnePerson()) {
                equipment.setFunctional(false);
                list.add(equipment);
            }
        }
        return list;
    }

    private List<Equipment> getClimbingEquipmentList(int personsMax) {
        List<Equipment> list = new ArrayList<>();
        for(int i = 0; i < personsMax; i++) {
            list.addAll(createClimbingEquipmentForOnePerson());
        }

        for (int i = 0; i < 2; i++) {
            for (Equipment equipment : createClimbingEquipmentForOnePerson()) {
                equipment.setFunctional(false);
                list.add(equipment);
            }
        }
        return list;
    }

    private List<Equipment> getGoKartEquipmentList(int personsMax) {
        List<Equipment> list = new ArrayList<>();
        for(int i = 0; i < personsMax; i++) {
            list.addAll(createGoKartEquipmentForOnePerson());
        }

        for (int i = 0; i < 2; i++) {
            for (Equipment equipment : createGoKartEquipmentForOnePerson()) {
                equipment.setFunctional(false);
                list.add(equipment);
            }
        }
        return list;
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

    private void createBookings(List<Activity> activities) {
        List<Booking> bookings = new ArrayList<>(List.of(
                new Booking("John the Baptist", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), activities.get(0).getPersonsMax(), activities.get(0)), // Paintball
                new Booking("Francis of Assisi", LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 0), 6, activities.get(0)), // Paintball 2 booking
                new Booking("Soeren Pind", LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(16, 0), 8, activities.get(0)), // Paintball 3 booking
                new Booking("Scooby-Doo", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), activities.get(1).getPersonsMax(), activities.get(1)), // Climbing
                new Booking("Mickey Mouse", LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 0), 6, activities.get(1)), // Climbing 2 booking
                new Booking("John Lennon", LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(16, 0), 8, activities.get(1)), // Climbing 3 booking
                new Booking("George Harrison", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), activities.get(2).getPersonsMax(), activities.get(2)), // Go-kart
                new Booking("Margrethe den Foerste", LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(14, 0), 6, activities.get(2)), // Go-kart 2 booking
                new Booking("Churchill", LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(16, 0), 8, activities.get(2)) // Go-kart 3 booking
        ));
        bookingRepository.saveAll(bookings);
    }


    // ---- Debugging method to delete an activity ---
    @Transactional
    public void deleteActivity() {
        List<Activity> activities = activityRepository.findAll();
        if (!activities.isEmpty()) {
            Activity activity = activities.get(0);
            System.out.println("Debug deleteActivity");
            System.out.println(" Activity: " + activity);
            Long activityId = activity.getId();
            List<Equipment> equipments = activity.getEquipmentList();
            Set<EquipmentType> equipmentTypes = activity.getEquipmentTypes();

            System.out.println(" Equipments: " + equipments);
            System.out.println(" EquipmentTypes: " + equipmentTypes);

            // Use activityService to delete the activity
            activityService.delete(activity);

            // check if activity is deleted
            Activity deletedActivity = activityRepository.findById(activityId).orElse(null);

            // check if equipment is deleted
            List<Equipment> deletedEquipments = findEquipmentList(equipments);

            // check if equipmentType is deleted
            Set<EquipmentType> deletedEquipmentTypes = findEquipmentTypeSet(equipmentTypes);

            System.out.println("Debug deleteActivity");
            System.out.println(" Deleted activity: " + deletedActivity);
            System.out.println(" Deleted equipments: " + deletedEquipments);
            System.out.println(" Deleted equipmentTypes: " + deletedEquipmentTypes);
        }
    }

    private List<Equipment> findEquipmentList(List<Equipment> equipments) {
        List<Equipment> foundEquipment = new ArrayList<>();
        for (Equipment equipment : equipments) {
            foundEquipment.add(equipmentService.get(equipment));
        }
        return foundEquipment;
    }

    private Set<EquipmentType> findEquipmentTypeSet(Set<EquipmentType> equipmentTypes) {
        Set<EquipmentType> foundEquipmentTypes = new HashSet<>();
        for (EquipmentType equipmentType : equipmentTypes) {
            foundEquipmentTypes.add(equipmentTypeService.get(equipmentType));
        }
        return foundEquipmentTypes;
    }

}