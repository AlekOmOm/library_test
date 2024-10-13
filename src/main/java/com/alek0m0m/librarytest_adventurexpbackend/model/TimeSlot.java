package com.alek0m0m.librarytest_adventurexpbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxParticipants;
    private int currentParticipants;
    private boolean available;  // updated availability automatically managed in setters

    // Default constructor
    public TimeSlot() {
    }

    // Parameterized constructor
    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, int maxParticipants, int currentParticipants, boolean available) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        // Automatically set availability based on current and max participants
        this.available = currentParticipants < maxParticipants;
    }

    // Getters and Setters with automatic availability updates

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
        // Automatically update availability when maxParticipants is set
        this.available = this.currentParticipants < this.maxParticipants;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
        // Automatically update availability when currentParticipants is set
        this.available = this.currentParticipants < this.maxParticipants;
    }

    public void addParticipants(int participants) {
        this.currentParticipants += participants;
        this.available = this.currentParticipants < this.maxParticipants;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // ----------------- Helper ---------------------
    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", maxParticipants=" + maxParticipants +
                ", currentParticipants=" + currentParticipants +
                ", available=" + available +
                '}';
    }

    public void updateFrom(TimeSlot timeSlot) {
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.maxParticipants = timeSlot.getMaxParticipants();
        setCurrentParticipants(timeSlot.getCurrentParticipants());
    }
}
