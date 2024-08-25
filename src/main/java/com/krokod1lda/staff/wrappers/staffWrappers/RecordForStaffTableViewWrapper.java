package com.krokod1lda.staff.wrappers.staffWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordForStaffTableViewWrapper {
    private long id;
    private String startHours;
    private String endHours;
    private float hours;
    private float workingRate;
}