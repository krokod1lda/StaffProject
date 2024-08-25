package com.krokod1lda.staff.wrappers.mainViewWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllObjectsForOneDayWrapper {
    private Date date;
    private List<AllFieldsForOneStaffWrapper> staffList;
}