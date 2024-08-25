package com.krokod1lda.staff.wrappers.recordWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordAndStaffNameWrapper {
    private long id;
    private String startHours;
    private String endHours;
    private float workingRate;
    private String staffName;
}
