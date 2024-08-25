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
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long staffId;
    @Column(columnDefinition = "DATE")
    private Date date;
    private String startHours; // По умолчанию для хранения времени применяется формат "hh:mm:ss". Занимает 3 байта.
    private String endHours;
    private float workingRate;

    public Record(long staffId, Date date, String startHours, String endHours, float workingRate) {
        this.staffId = staffId;
        this.date = date;
        this.startHours = startHours;
        this.endHours = endHours;
        this.workingRate = workingRate;
    }
}
