package com.outpatient.service;

import com.outpatient.entity.Appointment;
import com.outpatient.entity.Doctor;
import com.outpatient.entity.User;
import com.outpatient.repository.AppointmentRepository;
import com.outpatient.repository.DoctorRepository;
import com.outpatient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
    
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    // Schedule an appointment
//    public Appointment scheduleAppointment(String doctorName, String username, LocalDateTime appointmentTime) {
//        logger.info("Scheduling appointment for doctor: {} and user: {}", doctorName, username);
//        Doctor doctor = doctorRepository.findByName(doctorName)
//                .stream().findFirst() // Get first doctor with the name
//                .orElseThrow(() -> new RuntimeException("Doctor not found: " + doctorName));
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new RuntimeException("User not found: " + username);
//        }
//
//        Appointment appointment = new Appointment();
//        appointment.setDoctor(doctor);
//        appointment.setUser(user);
//        appointment.setAppointmentTime(appointmentTime);
//
//        return appointmentRepository.save(appointment);
//    }

    public Appointment scheduleAppointment(String doctorName, String username, LocalDateTime appointmentTime) {
        System.out.println("Scheduling appointment for doctor: " + doctorName + " and user: " + username);

        Doctor doctor = doctorRepository.findByName(doctorName)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Doctor not found: " + doctorName));
        System.out.println("Found doctor: " + doctor);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        System.out.println("Found user: " + user);

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setUser(user);
        appointment.setAppointmentTime(appointmentTime);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        System.out.println("Saved appointment: " + savedAppointment);
        return savedAppointment;
    }

    // Retrieve appointments by doctor's name
    public List<Appointment> getAppointmentsByDoctorName(String doctorName) {
        return appointmentRepository.findByDoctor_Name(doctorName);
    }

    public List<Appointment> getAppointmentsByDoctorUsername(String doctorUsername) {
        return appointmentRepository.findByDoctor_Username(doctorUsername);
    }


    // Retrieve appointments by user's name
    public List<Appointment> getAppointmentsByUsername(String username) {
        return appointmentRepository.findByUser_Username(username);
    }
}