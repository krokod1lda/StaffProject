package com.krokod1lda.staff.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
public class Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long staffId;
    private String type;
    private int price;
    @Column(columnDefinition = "DATE")
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Additional(Long staffId, String type, int price, Date date, String description) {
        this.staffId = staffId;
        this.type = type;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public Additional() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
