package com.krokod1lda.staff.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long staffId;
    @Column(columnDefinition = "DATE")
    private Date date;
    private String startHours; // По умолчанию для хранения времени применяется формат "hh:mm:ss". Занимает 3 байта.
    private String endHours;
    private float workingRate;

    public Record(Long staffId, Date date, String startHours, String endHours, float workingRate) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public float getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(float workingRate) {
        this.workingRate = workingRate;
    }
}
