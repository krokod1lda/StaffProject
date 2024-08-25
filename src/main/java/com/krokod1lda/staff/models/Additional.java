package com.krokod1lda.staff.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long staffId;
    private String type;
    private int price;
    @Column(columnDefinition = "DATE")
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Additional(long staffId, String type, int price, Date date, String description) {
        this.staffId = staffId;
        this.type = type;
        this.price = price;
        this.date = date;
        this.description = description;
    }
}
