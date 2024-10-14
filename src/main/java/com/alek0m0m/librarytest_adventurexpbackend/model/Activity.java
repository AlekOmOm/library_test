package com.alek0m0m.librarytest_adventurexpbackend.model;

import jakarta.persistence.*;
import com.Alek0m0m.library.jpa.BaseEntity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Activity extends BaseEntity<Long> {

    private String description;
    private int pricePrPerson;
    private int timeMaxLimit;
    private int ageMin;
    private int ageMax;
    private int personsMin;
    private int personsMax;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int timeSlotInterval;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id")
    private List<Equipment> equipmentList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id")
    private Set<EquipmentType> equipmentTypes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id")
    private List<TimeSlot> timeSlots;
    // ---------------Constructors----------------
    public Activity() {
    }

    public Activity(String name, String description, int pricePrPerson, int timeMaxLimit, int ageMin, int ageMax, int personsMin, int personsMax, LocalTime openingTime, LocalTime closingTime, int timeSlotInterval, List<Equipment> equipmentList, Set<EquipmentType> equipmentRequiredPerPerson) {
        this.name = name;
        this.description = description;
        this.pricePrPerson = pricePrPerson;
        this.timeMaxLimit = timeMaxLimit;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.personsMin = personsMin;
        this.personsMax = personsMax;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.timeSlotInterval = timeSlotInterval;
        this.equipmentList = equipmentList;
        this.equipmentTypes = equipmentRequiredPerPerson;
        this.timeSlots = initTimeSlots();
    }



    // --------------- Get and Set methods ----------------


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPricePrPerson() {
        return pricePrPerson;
    }

    public void setPricePrPerson(int pricePrPerson) {
        this.pricePrPerson = pricePrPerson;
    }


    //---------------Getters and Setters-----------------------
    public int getTimeMaxLimit() {
        return timeMaxLimit;
    }

    public void setTimeMaxLimit(int timeMaxLimit) {
        this.timeMaxLimit = timeMaxLimit;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public int getPersonsMin() {
        return personsMin;
    }

    public void setPersonsMin(int personsMin) {
        this.personsMin = personsMin;
    }

    public int getPersonsMax() {return personsMax;}

    public void setPersonsMax(int personsMax) {
        this.personsMax = personsMax;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public int getTimeSlotInterval() {
        return timeSlotInterval;
    }

    public void setTimeSlotInterval(int timeSlotInterval) {
        this.timeSlotInterval = timeSlotInterval;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Set<EquipmentType> getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(Set<EquipmentType> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void updateTimeSlot(TimeSlot timeSlot) {
        for (TimeSlot slot : this.timeSlots) {
            if (slot.getDate().equals(timeSlot.getDate()) &&
                    slot.getStartTime().equals(timeSlot.getStartTime()) &&
                    slot.getEndTime().equals(timeSlot.getEndTime())) {
                slot.updateFrom(timeSlot);
                break;
            }
        }
    }

    // --------------- helper ----------------

    private List<TimeSlot> initTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        int interval = timeSlotInterval;

        // Making the slots

        while (openingTime.isBefore(closingTime)) {
            LocalTime endTime = openingTime.plusMinutes(interval);
            if (endTime.isAfter(closingTime)) {
                endTime = closingTime;
            }

            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(openingTime);
            timeSlot.setEndTime(endTime);


            timeSlot.setMaxParticipants(personsMax);
            timeSlot.setCurrentParticipants(0);
            timeSlot.setAvailable(true);

            timeSlots.add(timeSlot);

            openingTime = endTime;
        }

        return timeSlots;
    }

    public void updateFrom(Activity other) {
        this.setName(other.getName());
        this.setDescription(other.getDescription());
        this.setPricePrPerson(other.getPricePrPerson());
        this.setTimeMaxLimit(other.getTimeMaxLimit());
        this.setAgeMin(other.getAgeMin());
        this.setAgeMax(other.getAgeMax());
        this.setPersonsMin(other.getPersonsMin());
        this.setPersonsMax(other.getPersonsMax());
        this.setOpeningTime(other.getOpeningTime());
        this.setClosingTime(other.getClosingTime());
        this.setTimeSlotInterval(other.getTimeSlotInterval());
        this.setEquipmentList(other.getEquipmentList());
        this.setEquipmentTypes(other.getEquipmentTypes());
    }




    @Override
    public String toString() {
        return "Activity{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePrPerson=" + pricePrPerson +
                ", timeMaxLimit=" + timeMaxLimit +
                ", ageMin=" + ageMin +
                ", ageMax=" + ageMax +
                ", personsMin=" + personsMin +
                ", personsMax=" + personsMax +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", timeSlotInterval=" + timeSlotInterval +
                ", equipmentList=" + equipmentList +
                ", equipmentTypes=" + equipmentTypes +
                ", timeSlots=" + timeSlots +
                '}';
    }

}