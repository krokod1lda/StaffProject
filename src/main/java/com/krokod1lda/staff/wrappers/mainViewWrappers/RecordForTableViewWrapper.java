package com.krokod1lda.staff.wrappers.mainViewWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordForTableViewWrapper {
    private long id;
    private String startHours;
    private String endHours;
    private float hours;
    private float workingRate;
}
