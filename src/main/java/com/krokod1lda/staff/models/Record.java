package com.krokod1lda.staff.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long staffId;
    private String date; // MySQL извлекает и выводит величины DATE в формате 'YYYY-MM-DD'
    private String startHours; // По умолчанию для хранения времени применяется формат "hh:mm:ss". Занимает 3 байта.
    private String endHours;
    private double workingRate;

    public Record(Long staffId, String date, String startHours, String endHours, double workingRate) {
        this.staffId = staffId;
        this.date = date;
        this.startHours = startHours;
        this.endHours = endHours;
        this.workingRate = workingRate;
    }

    public Record() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartHours() {
        return startHours;
    }

    public void setStartHours(String startHours) {
        this.startHours = startHours;
    }

    public String getEndHours() {
        return endHours;
    }

    public void setEndHours(String endHours) {
        this.endHours = endHours;
    }

    public double getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(double workingRate) {
        this.workingRate = workingRate;
    }
}
