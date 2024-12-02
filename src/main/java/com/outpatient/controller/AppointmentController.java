package com.outpatient.controller;

import com.outpatient.dto.AppointmentRequest;
import com.outpatient.entity.Appointment;
import com.outpatient.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Schedule an appointment
//    @PostMapping("/schedule")
//    public ResponseEntity<Appointment> scheduleAppointment(
//            @RequestParam String doctorName,
//            @RequestParam String username,
//            @RequestParam String appointmentTime) {
//
//        LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentTime); // Convert string to LocalDateTime
//        Appointment scheduledAppointment = appointmentService.scheduleAppointment(doctorName, username, appointmentDateTime);
//        return new ResponseEntity<>(scheduledAppointment, HttpStatus.CREATED);
//    }
    @PostMapping("/schedule")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        System.out.println("Received request to schedule appointment.");
        System.out.println("Doctor Name: " + appointmentRequest.getDoctorName());
        System.out.println("Username: " + appointmentRequest.getUsername());
        System.out.println("Appointment Time: " + appointmentRequest.getAppointmentTime());

        LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentRequest.getAppointmentTime());
        Appointment scheduledAppointment = appointmentService.scheduleAppointment(
                appointmentRequest.getDoctorName(),
                appointmentRequest.getUsername(),
                appointmentDateTime
        );

        System.out.println("Scheduled appointment: " + scheduledAppointment);
        return new ResponseEntity<>(scheduledAppointment, HttpStatus.CREATED);
    }

    // Get appointments by doctor's name
//    @GetMapping("/doctor/{doctorName}")
//    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorName(@PathVariable String doctorName) {
//        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorName(doctorName);
//        if (appointments.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(appointments, HttpStatus.OK);
//    }

    @GetMapping("/doctor/{doctorUsername}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorName(@PathVariable String doctorUsername) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorUsername(doctorUsername);
        return ResponseEntity.ok(appointments);
    }
    // Get appointments by user's username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUsername(@PathVariable String username) {
        List<Appointment> appointments = appointmentService.getAppointmentsByUsername(username);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}