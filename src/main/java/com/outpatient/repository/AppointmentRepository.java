package com.outpatient.repository;

import com.outpatient.entity.Appointment;
import com.outpatient.entity.Doctor;
import com.outpatient.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Retrieve appointments by doctor
    @EntityGraph(attributePaths = {"user", "doctor"})
    List<Appointment> findByDoctor_Name(String doctorName);

    List<Appointment> findByDoctor_Username(String doctorUsername);

    // Retrieve appointments by user
    List<Appointment> findByUser_Username(String username);
}