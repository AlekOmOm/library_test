package com.alek0m0m.librarytest_adventurexpbackend.service;

import org.example.adventurexpbackend.model.Activity;
import org.example.adventurexpbackend.model.Booking;
import org.example.adventurexpbackend.model.TimeSlot;
import org.example.adventurexpbackend.repository.ActivityRepository;
import org.example.adventurexpbackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeslotService {

    private final ActivityRepository activityRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public TimeslotService(ActivityRepository activityRepository, BookingRepository bookingRepository) {
        this.activityRepository = activityRepository;
        this.bookingRepository = bookingRepository;
    }



    //------------------------------------------------------------------------------------------------------------------
    // Method to book a timeslot for a specific activity
    public String bookTimeSlot(Long activityId, Long timeslotId, Booking booking) {
        Optional<Activity> activityOptional = activityRepository.findById(activityId);

        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();

            Optional<TimeSlot> timeSlotOptional = activity.getTimeSlots().stream()
                    .filter(ts -> ts.getId().equals(timeslotId))
                    .findFirst();

            if (timeSlotOptional.isPresent()) {
                TimeSlot timeSlot = timeSlotOptional.get();

                // Check if the timeslot is free
                if (timeSlot.isAvailable()) {
                    // Set timeslot to not available when boked
                    timeSlot.setAvailable(false);

                    activityRepository.save(activity);

                    // Associate the booking with the timeslot and activity
                    booking.setActivity(activity);
                    booking.setStartTime(timeSlot.getStartTime());
                    booking.setEndTime(timeSlot.getEndTime());
                    booking.setDate(timeSlot.getDate());

                    // Save book
                    bookingRepository.save(booking);

                    return "Timeslot booked";
                } else {return "Timeslot is not free";}
            } else {return "Timeslot is not found.";}
        } else {return "Activity were not found.";}
    }
}

