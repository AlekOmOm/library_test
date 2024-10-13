package com.alek0m0m.librarytest_adventurexpbackend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int personsAmount;
    private String participantName; //Name of participant who made the booking

    @ManyToOne(optional = true)
    @JoinColumn(name = "activity", referencedColumnName = "id")
    Activity activity;


    public Booking() {
    }

    public Booking(String participantName,LocalDate date, LocalTime startTime, LocalTime endTime, int personsAmount, Activity activity) {
        this.participantName = participantName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.personsAmount = personsAmount;
        this.activity = activity;
    }


    //----------------------getters and setters------------------------
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

    public int getPersonsAmount() {
        return personsAmount;
    }

    public void setPersonsAmount(int personsAmount) {
        this.personsAmount = personsAmount;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //----------------------------------------------------------------
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", personsAmount=" + personsAmount +
                ", activity=" + activity +
                ", participantName='" + participantName + '\'' +
                '}';
    }
}
