package com.krokod1lda.staff.wrappers.mainViewWrappers;

import com.krokod1lda.staff.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllFieldsForOneStaffWrapper {
    private Staff staff;
    private List<RecordForTableViewWrapper> records;
    private float prizes;
    private float sales;
    private float fines;
    private float result;
}