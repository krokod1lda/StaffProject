package com.krokod1lda.staff.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String staffId;
    private Date date; // MySQL извлекает и выводит величины DATE в формате 'YYYY-MM-DD'
    private Time startHours; // По умолчанию для хранения времени применяется формат "hh:mm:ss". Занимает 3 байта.
    private Time endHours;
    private double workingRate;

    public Record(String staffId, Date date, Time startHours, Time endHours, double workingRate) {
        this.staffId = staffId;
        this.date = date;
        this.startHours = startHours;
        this.endHours = endHours;
        this.workingRate = workingRate;
    }
    public Record() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartHours() {
        return startHours;
    }

    public void setStartHours(Time startHours) {
        this.startHours = startHours;
    }

    public Time getEndHours() {
        return endHours;
    }

    public void setEndHours(Time endHours) {
        this.endHours = endHours;
    }

    public double getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(double workingRate) {
        this.workingRate = workingRate;
    }
}
