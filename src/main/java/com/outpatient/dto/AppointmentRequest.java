package com.outpatient.dto;

public class AppointmentRequest {
    private String doctorName;
    private String username;
    private String appointmentTime; // ISO-8601 format (e.g., 2024-11-27T15:00:00)

    // Getters and Setters
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}