package com.alek0m0m.librarytest_adventurexpbackend.model;

import jakarta.persistence.*;
import com.Alek0m0m.library.jpa.BaseEntity;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Booking extends BaseEntity<Long> {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int personsAmount;
    @AttributeOverride(name = "name", column = @Column(name = "participantName"))


    @ManyToOne(optional = true)
    @JoinColumn(name = "activity", referencedColumnName = "id")
    Activity activity;


    public Booking() {
    }

    public Booking(String participantName,LocalDate date, LocalTime startTime, LocalTime endTime, int personsAmount, Activity activity) {
        this.name = participantName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.personsAmount = personsAmount;
        this.activity = activity;
    }


    //----------------------getters and setters------------------------

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
        return name;
    }

    public void setParticipantName(String participantName) {
        this.name = participantName;
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
                "id=" + getId() +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", personsAmount=" + personsAmount +
                ", activity=" + activity +
                ", participantName='" + name + '\'' +
                '}';
    }
}
